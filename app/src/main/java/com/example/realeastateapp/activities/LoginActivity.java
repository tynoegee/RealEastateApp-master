package com.example.realeastateapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private String LOG = LoginActivity.class.getSimpleName();
    private Authentications authentications = new Authentications();
    private SharedPref sharedPref = new SharedPref(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView newUser = findViewById(R.id.newUser);
        newUser.setOnClickListener((v) -> {

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener((v) -> {

            String strUsername = username.getText().toString().trim();
            String strPassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(strUsername))
                username.setError("Enter your username");
            else if (TextUtils.isEmpty(strPassword)) {
                password.setError("Enter your password");
            } else {
                // Volley code

                authentications.setUsername(strUsername);
                authentications.setPassword(strPassword);
                StringRequest myStringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_LOGIN, response -> {

                    try {

                        // gets response from php file for success or failure
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d(LOG, response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");

                        switch (code) {
                            case "Login success":
                                if (message.equals("Admin")) {
                                    sharedPref.saveString("username", authentications.getUsername());
                                    sharedPref.saveString("password", authentications.getPassword());
                                    Intent intent = new Intent(this, AdminActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else if (message.equals("User")) {
                                    sharedPref.saveString("username", authentications.getUsername());
                                    sharedPref.saveString("password", authentications.getPassword());
                                    Intent intent = new Intent(this, UserActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                            .putExtra("user", authentications.getUsername());
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
                        Log.d(LOG, error.toString());

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
                        String dateTime = Calendar.getInstance().getTime().toString();
                        params.put("Username", authentications.getUsername());
                        params.put("Password", authentications.getPassword());
                        params.put("Login", dateTime);

                        return params;

                    }
                };

                AppSingleton.getInstance(this).addToRequestQueue(myStringRequest);

            }
        });
    }

    private void login() {

    }
}
