package com.example.realeastateapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.example.realeastateapp.adapters.DeleteUserAdapter;
import com.example.realeastateapp.models.UserModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.RecyclerItemTouchHelperDeleteUser;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserListActivity extends AppCompatActivity implements RecyclerItemTouchHelperDeleteUser.RecyclerItemTouchHelperListener{
    private RecyclerView deleteUserRecyclerView;
    private ArrayList<UserModal> userModals= new ArrayList<>();
    private DeleteUserAdapter userAdapter;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Delete Users");
        }

        deleteUserRecyclerView = findViewById(R.id.chooseTeamRecyclerView);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        deleteUserRecyclerView.setLayoutManager(layoutManager);
        deleteUserRecyclerView.setHasFixedSize(true);

        deleteUserRecyclerView.setItemAnimator(new DefaultItemAnimator());
        deleteUserRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperDeleteUser(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(deleteUserRecyclerView);

        fetchStudents();
    }

    private void fetchStudents() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.URL_FETCH_USERS, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int j = 0; j < jsonArray.length();j++){
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");
                    ArrayList<String> usernameValues = new ArrayList<>();
                    ArrayList<String> emailValues = new ArrayList<>();
                    usernameValues.add(username);
                    emailValues.add(email);
                    for(int i = 0; i<usernameValues.size(); i++){
                        userModals.add(new UserModal(usernameValues.get(i), emailValues.get(i)));
                    }

                    userAdapter = new DeleteUserAdapter(this, userModals);
                }
                deleteUserRecyclerView.setAdapter(userAdapter);
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
        if (viewHolder instanceof DeleteUserAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = userModals.get(viewHolder.getAdapterPosition()).getLabel();


            // backup of removed item for undo purpose
            final UserModal deleteItem = userModals.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            userAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " remove user!", Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", view -> {

                // undo is selected, restore the deleted item
                userAdapter.restoreItem(deleteItem, deleteIndex);
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {


                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {

                        deleteUser(name);
                    }

                }


            });
        }
    }

    private void deleteUser(String user) {
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_DELETE_PLAYER, response -> {
            try {

                // gets response from php file for success or failure
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");

                switch (code) {

                    case "successful":

                        Toast.makeText(this, "User deleted", Toast.LENGTH_LONG).show();

                        break;
                    case "Login failed":

                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {

            if (error instanceof TimeoutError) {
                Toast.makeText(this, "Delete attempt has timed out. Please try again.",
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
                params.put("Username", user);
                return params;

            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(myStringRequest);

    }
}
