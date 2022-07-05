package com.example.realeastateapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.Authentications;
import com.example.realeastateapp.utilities.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private SharedPref sharedPref = new SharedPref(this);
    private Authentications authentications = new Authentications();
    private static final int TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            //if user once logged in login info
            //is queried from shared preferences
            // and checked for validity

            final String sharedUsername = sharedPref.getString("username", "");
            final String sharedPassword = sharedPref.getString("password", "");
            // final String sharedSport = sharedPref.getString("sport", "");

            new Handler().postDelayed(() -> {

                if (!(sharedPassword.equals("") && sharedUsername.equals(""))) {
                    login();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },TIME_OUT);
        }


    private void login() {
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_LOGIN_CHECK, response -> {

            try {

                // gets response from php file for success or failure
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                switch (code) {
                    case "Login success":
                        if (message.equals("Admin")) {
                            Intent intent = new Intent(this, AdminActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (message.equals("User")) {
                            Intent intent = new Intent(this, UserActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .putExtra("user", sharedPref.getString("username", ""));
                            startActivity(intent);

                        }
                        break;
                    case "Login failed":
                        Toast.makeText(this, "You entered an incorrect password/username", Toast.LENGTH_LONG).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {

            if (error instanceof TimeoutError) {
                Toast.makeText(this, "Login attempt has timed out. Please try again.",
                        Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();

            }
            error.printStackTrace();

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {

                // stores the login details using key pair system
                Map<String, String> params = new HashMap<>();
                params.put("Username", sharedPref.getString("username", ""));
                params.put("Password", sharedPref.getString("password", ""));

                return params;

            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(myStringRequest);

    }
}
