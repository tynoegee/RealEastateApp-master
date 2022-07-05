package com.example.realeastateapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.activities.UserActivity;
import com.example.realeastateapp.models.ValueProperty;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddPropertyFragment extends Fragment {
    private static final int GALLERY_REQUEST = 1200;

    public AddPropertyFragment() {
    }

    // Create a class that will calculate the values given for the property being mentioned then post that value to db

    private TextView propertyValue;
    private EditText phone;
    private EditText bedrooms;
    private EditText bathrooms;
    private EditText surburb;
    private Spinner houseType;
    private Spinner location_type;
    private EditText description;
    private EditText address;
    private EditText city;
    private EditText houseSize;
    private Spinner sellRent;
    private  int calculatedValue = 0;
    private ValueProperty valueProperty = new ValueProperty();
    private GalleryPhoto galleryPhoto;
    private  LinearLayout imageLinearLayout;
    private static final String LOG = AddPropertyFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_property, container, false);
        Button calculate = view.findViewById(R.id.calculatePropertyValue);
        Button submit = view.findViewById(R.id.submitPropertyValue);
        propertyValue = view.findViewById(R.id.propertyValue);
        phone = view.findViewById(R.id.phone);
        bedrooms = view.findViewById(R.id.bedrooms);
        bathrooms = view.findViewById(R.id.restrooms);
        houseSize = view.findViewById(R.id.yardSize);
        houseType = view.findViewById(R.id.houseType);
        surburb = view.findViewById(R.id.suburb);
        location_type = view.findViewById(R.id.location_type);
        address = view.findViewById(R.id.addressProp);
        description = view.findViewById(R.id.descriProp);
        city = view.findViewById(R.id.cityProp);
        sellRent = view.findViewById(R.id.sellRent);
        galleryPhoto = new GalleryPhoto(view.getContext());



        calculate.setOnClickListener((v)->{
            int bedroomsValue =Integer.parseInt(bedrooms.getText().toString());
            int bathroomValue =Integer.parseInt(bathrooms.getText().toString());
            int houseSizeValue =Integer.parseInt(houseSize.getText().toString());
            String locationTypeValue = location_type.getSelectedItem().toString();
            if (sellRent.getSelectedItem().toString().equals("Sell")){
                calculatedValue = valueProperty.calHomeSell(bedroomsValue, bathroomValue,locationTypeValue , houseSizeValue);
            }
            else if (sellRent.getSelectedItem().toString().equals("Rent")){
                calculatedValue = valueProperty.calHomeRent(bedroomsValue,bathroomValue,locationTypeValue,houseSizeValue);
            }

            propertyValue.setText(String.format("The Property has been valued at $%s",calculatedValue));


        });



        submit.setOnClickListener((v) ->{

            validate();

            valueProperty.setAddress(address.getText().toString());
            valueProperty.setAmount(String.valueOf(calculatedValue));
            valueProperty.setCity(city.getText().toString());
            valueProperty.setHouseSize(houseSize.getText().toString());
            valueProperty.setHouseType(houseType.getSelectedItem().toString());
            valueProperty.setPhone_number(phone.getText().toString());
            valueProperty.setSellRent(sellRent.getSelectedItem().toString());
            valueProperty.setNumRestrooms(bathrooms.getText().toString());
            valueProperty.setDescription(description.getText().toString());
            valueProperty.setSuburb(surburb.getText().toString());
            valueProperty.setNumRooms(bedrooms.getText().toString());
            valueProperty.setLocationType(location_type.getSelectedItem().toString());


                    postProperty();

        });

        return view;
    }

    private void validate() {

    }

    private void postProperty() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_GET_SAVED_HOUSE, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                    JSONObject index = jsonArray.getJSONObject(0);
                    String code = index.getString("code");
                    String message = index.getString("message");

                    if(code.equals("successful")){
                        Intent intent = new Intent(getContext(), UserActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else if(code.equals("failed")){
                        Toast.makeText(getContext(), "Attempt has failed.",
                                Toast.LENGTH_LONG).show();
                    }


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
                 params.put("address", valueProperty.getAddress());
                params.put("price",valueProperty.getAmount());
                params.put("city",valueProperty.getCity());
                params.put("area",valueProperty.getHouseSize());
                params.put("houseType", valueProperty.getHouseType());
                params.put("owners_number",valueProperty.getPhone_number());
                params.put("sell_rent",valueProperty.getSellRent());
                params.put("bathrooms", valueProperty.getNumRestrooms());
                params.put("description",valueProperty.getDescription());
                params.put("surburb",valueProperty.getSuburb());
                params.put("bedrooms", valueProperty.getNumRooms());
                params.put("location_type", valueProperty.getLocationType());
                return params;
            }
        };
        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
