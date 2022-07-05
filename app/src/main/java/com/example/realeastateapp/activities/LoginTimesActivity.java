package com.example.realeastateapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.adapters.LogAdapter;
import com.example.realeastateapp.models.LoginTimes;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginTimesActivity extends AppCompatActivity {
    private RecyclerView logView;
    private ArrayList<LoginTimes> loginTimes = new ArrayList<>();
    private LogAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("View Login and Logout Times");
        }

        logView = findViewById(R.id.logRecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        logView.setLayoutManager(layoutManager);
        logView.addItemDecoration(itemDecoration);
        logView.setHasFixedSize(true);
        fetchLogs();




    }

    private void fetchLogs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.URL_GET_LOGIN_TIMES, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String username = jsonObject.getString("username");
                    String login_time = jsonObject.getString("login_time");
                    String logout_time = jsonObject.getString("logout_time");

                    loginTimes.add(new LoginTimes(username, login_time, logout_time));

                    adapter = new LogAdapter(this, loginTimes);
                }


                logView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            if (error instanceof TimeoutError) {
                Toast.makeText(this, "Attempt has timed out. Please try again.",
                        Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();

            }
            error.printStackTrace();


        });

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
