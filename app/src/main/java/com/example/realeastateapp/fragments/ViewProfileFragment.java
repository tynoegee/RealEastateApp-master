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
import com.example.realeastateapp.adapters.ProfileAdapter;
import com.example.realeastateapp.models.UserModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileFragment extends Fragment {
    private RecyclerView viewProfileRecyclerView;
    private ProfileAdapter profileAdapter;
    private ArrayList<UserModal> userModals = new ArrayList<>();
    public ViewProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        viewProfileRecyclerView = view.findViewById(R.id.viewProfile);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        viewProfileRecyclerView.setLayoutManager(layoutManager);
        viewProfileRecyclerView.setHasFixedSize(true);
        viewProfileRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        retrieveData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userModals.clear();
    }

    private void retrieveData() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_GET_DETAILS, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject index = jsonArray.getJSONObject(i);

                    String username = index.getString("username");
                    String fname = index.getString("fname");
                    String lname = index.getString("lname");
                    String email = index.getString("email");
                    String company = index.getString("company");
                    String service = index.getString("services");
                    String phoneNum = index.getString("phone");
                    String whatsappNum = index.getString("whatsapp");
                    String city = index.getString("city");
                    String address = index.getString("address");

                    ArrayList<String> values = new ArrayList<>();
                    values.add(username);
                    values.add(fname);
                    values.add(lname);
                    values.add(email);
                    values.add(company);
                    values.add(service);
                    values.add(phoneNum);
                    values.add(whatsappNum);
                    values.add(city);
                    values.add(address);

                    String[] label = {"Username", "First Name", "Last Name", "Email", "Company Name",
                    "Service", "Phone Number", "Whatsapp Number", "City", "Address"};

                    for(int j = 0; j < values.size(); j++) {
                        userModals.add(new UserModal(label[j], values.get(j)));
                    }

                }
                profileAdapter = new ProfileAdapter(getContext(), userModals);
               viewProfileRecyclerView.setAdapter(profileAdapter);
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


        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPref sharedPref = new SharedPref(getView().getContext());
                params.put("username", sharedPref.getString("username", ""));
                return params;

            }
        };
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);





    }

}
