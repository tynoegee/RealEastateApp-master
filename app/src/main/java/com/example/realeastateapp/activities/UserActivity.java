package com.example.realeastateapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.fragments.AddPropertyFragment;
import com.example.realeastateapp.fragments.EditProfileFragment;
import com.example.realeastateapp.fragments.SavedFragment;
import com.example.realeastateapp.fragments.SearchFragment;
import com.example.realeastateapp.fragments.TrackPropertiesFragment;
import com.example.realeastateapp.fragments.ViewProfileFragment;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPref sharedPref = new SharedPref(this);
    private View mHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // NavigationView Header
        mHeaderView =  navigationView.getHeaderView(0);

        TextView username = mHeaderView.findViewById(R.id.userID);
        ImageView dp = mHeaderView.findViewById(R.id.userImage);



        Picasso.get().load("http://192.168.137.1/realestateapp/assests/profile_pics/grace.jpg")
                .placeholder(R.drawable.ic_report_problem_black_24dp)
                .error(R.drawable.ic_report_problem_black_24dp)
                .into(dp);



        String user = getIntent().getStringExtra("user");
        username.setText(user.toUpperCase());

        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(new ViewProfileFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sharedPref.clear();
            Intent intent = new Intent(this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_profile) {
            loadFragment(new ViewProfileFragment());
        } else if (id == R.id.nav_add_property) {

            loadFragment(new AddPropertyFragment());
        } else if (id == R.id.nav_edit_profile) {
            loadFragment(new EditProfileFragment());

        } else if (id == R.id.nav_track_properties) {
            loadFragment(new TrackPropertiesFragment());
        }
        else if (id == R.id.nav_properties) {
            loadFragment(new SearchFragment());
        }else if (id == R.id.nav_saved) {
            loadFragment(new SavedFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frags, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_POST_LOGOUT_TIME, response -> {

            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if (code.equals("successful")) {

                    sharedPref.clear();
                    Intent intent = new Intent(this, LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else if (code.equals("failed")) {

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

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

            @Override
            protected Map<String, String> getParams() {

                // stores the login details using key pair system
                String dateTime = Calendar.getInstance().getTime().toString();
                Map<String, String> params = new HashMap<>();
                params.put("Username",sharedPref.getString("username", ""));
                params.put("Logout", dateTime);

                return params;

            }

        };
        AppSingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
