package com.example.realeastateapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.adapters.RequestAdapter;
import com.example.realeastateapp.models.UserModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.RecyclerItemTouchHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApproveDeclineActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private RecyclerView requestList;
    private RequestAdapter requestAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<UserModal> userModals = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_decline);
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Approve or Decline Requests");
       requestList = findViewById(R.id.declineEvents);
       coordinatorLayout = findViewById(R.id.coordinatorLayout);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        requestList.setLayoutManager(layoutManager);
        requestList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        requestList.setHasFixedSize(true);
        approve();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(requestList);
    }

    private  void approve() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.GET_PROPS, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject index = jsonArray.getJSONObject(i);

                    String regnumberSport = index.getString("username");
                    String firstnameSport = index.getString("ref");
                    String surnameSport = index.getString("status");

                            userModals.add(new UserModal(regnumberSport, firstnameSport, surnameSport));

                        requestAdapter = new RequestAdapter(this, userModals);
                }

                requestList.setAdapter(requestAdapter);
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



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RequestAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = userModals.get(viewHolder.getAdapterPosition()).getLabel();
            String ref= userModals.get(viewHolder.getAdapterPosition()).getValue();


            // backup of removed item for undo purpose
            final UserModal selectedItem = userModals.get(viewHolder.getAdapterPosition());
            final int selectedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            requestAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " selected player!", Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", view -> {

                // undo is selected, restore the deleted item
                requestAdapter.restoreItem(selectedItem, selectedIndex);
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {


                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {

                        addUser(name, ref);
                    }

                }


            });
        }
    }

    private void addUser(String user, String ref) {
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_APPROVE, response -> {

            try {

                // gets response from php file for success or failure
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");

                switch (code) {

                    case "successful":

                        Toast.makeText(this, "Approved", Toast.LENGTH_LONG).show();

                        break;
                    case "failed":

                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {

            if (error instanceof TimeoutError) {
                Toast.makeText(this, "Submission attempt has timed out. Please try again.",
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
                Map<String, String> params = new HashMap<>();
                params.put("User", user);
                params.put("ref", ref);
                return params;

            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(myStringRequest);

    }
}
