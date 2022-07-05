package com.example.realeastateapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    private final Authentications mAuthentications = new Authentications();
    private SharedPref sharedPref = new SharedPref(this);
    private EditText username;
    private EditText pass;
    private EditText cnfmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.newUserID);
        pass = findViewById(R.id.pass);
        cnfmPass = findViewById(R.id.confirmPass);
        Button submit = findViewById(R.id.passSubmit);

        submit.setOnClickListener((v) -> {

            validate();
            String strPass = pass.getText().toString().trim();
            String strCnfmPass = cnfmPass.getText().toString().trim();
            String strUsername = username.getText().toString().trim();

            if (strPass.equals(strCnfmPass)) {
                if (strCnfmPass.length() >= 6) {

                    mAuthentications.setPassword(strCnfmPass);
                    mAuthentications.setUsername(strUsername);
                    register();
                } else {

                    Toast.makeText(this, "Password too short minimum 6 characters", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            }

        });

    }


    private void register() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_REGISTER, response -> {
            try {

                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");

                if (code.equals("successful")) {

                    sharedPref.saveString("username", mAuthentications.getUsername());
                    sharedPref.saveString("password", mAuthentications.getPassword());
                    Intent intent = new Intent(this, UserActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .putExtra("user",sharedPref.getString("username", ""));
                    startActivity(intent);


                } else if (code.equals("failed")) {
                    //Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();

                }


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
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Username", mAuthentications.getUsername());
                params.put("Password", mAuthentications.getPassword());

                return params;
            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void validate() {

        String strPassword = pass.getText().toString().trim();
        String strCnfmPassword = cnfmPass.getText().toString().trim();
        String strUsername = username.getText().toString().trim();

        if (strUsername.isEmpty()) {
            username.setError("Enter your Username");

        } else {
            username.setError(null);

        }
        if (strPassword.isEmpty()) {
            pass.setError("Enter your Password");

        } else {
            pass.setError(null);

        }

        if (strCnfmPassword.isEmpty()) {
            cnfmPass.setError("Enter your Password");
        } else {
            cnfmPass.setError(null);

        }

    }
}
