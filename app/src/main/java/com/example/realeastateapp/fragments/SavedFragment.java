package com.example.realeastateapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.adapters.SavedAdapter;
import com.example.realeastateapp.models.HousesModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SavedFragment extends Fragment {

    public SavedFragment() {
    }
private RecyclerView recyclerView;
    private ArrayList<HousesModal> housesModals = new ArrayList<>();
    private SavedAdapter savedAdapter = new SavedAdapter();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.savedRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));



getSavedHouses();

        return view;
    }

    private void getSavedHouses(){

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_GET_SAVED_HOUSE, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < (jsonArray.length()); i++) {
                    JSONObject index = jsonArray.getJSONObject(i);
                    String price = index.getString("price");
                    String bedrooms = index.getString("bedrooms");
                    String bathrooms = index.getString("bathrooms");
                    String area = index.getString("area");
                    String description = index.getString("description");
                    String address = index.getString("address");
                    String owners_number = index.getString("owners_number");
                    String city = index.getString("city");
                    String location_type = index.getString("location_type");
                    String surburb = index.getString("surburb");
                    String sell_rent = index.getString("sell_rent");
                    String houseType = index.getString("houseType");
                    String reference = index.getString("reference_number");

                    housesModals.add(new HousesModal(description, price, bedrooms, bathrooms,
                            area, address,city, owners_number, location_type, surburb, sell_rent, houseType, reference));


                }
                savedAdapter = new SavedAdapter(getContext(), housesModals);
                recyclerView.setAdapter(savedAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Attempt has timed out. Please try again.",
                        Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server is down", Toast.LENGTH_LONG).show();

            }
            error.printStackTrace();


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPref sharedPref = new SharedPref(getContext());
                String username = sharedPref.getString("username", "");
                params.put("username", username);
                return params;
            }
        };
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);




    }
}
