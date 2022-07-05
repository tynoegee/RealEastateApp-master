package com.example.realeastateapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.realeastateapp.adapters.SearchAdapter;
import com.example.realeastateapp.models.HousesModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private static final String LOG = SearchFragment.class.getSimpleName();
    private SearchView searchView;
    private RecyclerView recyclerViewProperties;
    private SearchAdapter searchAdapter;
    private ArrayList<HousesModal> housesModals = new ArrayList<>();
    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerViewProperties = view.findViewById(R.id.recyclerViewProperties);
        searchView = view.findViewById(R.id.search_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewProperties.setLayoutManager(layoutManager);
        recyclerViewProperties.setHasFixedSize(true);
        recyclerViewProperties.addItemDecoration( new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        getHouses();


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                searchAdapter.getFilter().filter(query);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void getHouses(){

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.URL_GET_HOUSES, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                Log.d(LOG, String.valueOf(jsonArray.length()));
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
                searchAdapter = new SearchAdapter(getContext(), housesModals);
                recyclerViewProperties.setAdapter(searchAdapter);
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


        });
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);





    }
}
