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

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.adapters.TrackAdapter;
import com.example.realeastateapp.models.TrackModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackPropertiesFragment extends Fragment {
    public TrackPropertiesFragment() {
    }

    private RecyclerView trackPropertyRecyclerView;
    private TrackAdapter trackAdapter;
    private ArrayList<TrackModal> trackModals = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_property, container, false);

        trackPropertyRecyclerView = view.findViewById(R.id.trackProperty);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        trackPropertyRecyclerView.setLayoutManager(layoutManager);
        trackPropertyRecyclerView.setHasFixedSize(true);
        trackPropertyRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        getTrackedProperties();


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void getTrackedProperties() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_GET_TRACK,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String address = jsonObject.getString("address");
                            String houseType = jsonObject.getString("house_type");
                            String sell_rent = jsonObject.getString("sell_rent");
                            String status = jsonObject.getString("status");

                            trackModals.add(new TrackModal(address, sell_rent, status, houseType));
                            trackAdapter = new TrackAdapter(getContext(), trackModals);

                        }

                        trackPropertyRecyclerView.setAdapter(trackAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                    if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(), "Login attempt has timed out. Please try again.",
                                Toast.LENGTH_LONG).show();

                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();

                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(), "Server is down", Toast.LENGTH_LONG).show();

                    }
                    error.printStackTrace();
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPref sharedPref = new SharedPref(getContext());
                params.put("username", sharedPref.getString("username", ""));

                return params;
            }
        };
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}
