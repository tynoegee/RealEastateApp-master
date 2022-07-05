package com.example.realeastateapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realeastateapp.R;
import com.example.realeastateapp.utilities.SharedPref;

public class AdminActivity extends AppCompatActivity {
    private SharedPref sharedPref = new SharedPref(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        TextView userList = findViewById(R.id.userList);
        TextView houseList = findViewById(R.id.houseList);
        TextView approveDecline = findViewById(R.id.approveDecline);
        TextView loginTimes = findViewById(R.id.loginTimes);

        loginTimes.setOnClickListener((v)->{
            Intent intent = new Intent(this, LoginTimesActivity.class);
            startActivity(intent);
        });
        userList.setOnClickListener((v)->{
            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        });
        approveDecline.setOnClickListener((v)->{
            Intent intent = new Intent(this, ApproveDeclineActivity.class);
            startActivity(intent);
        });
       houseList.setOnClickListener((v)->{
            Intent intent = new Intent(this, HouseListActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            sharedPref.clear();
            Intent intent = new Intent(this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
