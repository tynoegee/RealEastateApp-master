package com.example.realeastateapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.realeastateapp.R;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;
import com.example.realeastateapp.utilities.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FullPropertyViewFragment extends Fragment {

private ImageView like;
private  String action;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_full_property_view, container, false);

        TextView price = view.findViewById(R.id.priceValue);
        TextView type = view.findViewById(R.id.typeValue);
        TextView reference = view.findViewById(R.id.referenceValue);
        TextView bedrooms = view.findViewById(R.id.bedroomsValue);
        TextView bathrooms = view.findViewById(R.id.bathroomsValue);
        TextView area = view.findViewById(R.id.areaValue);
        TextView property = view.findViewById(R.id.propertyDescription);
        TextView address = view.findViewById(R.id.addressValues);
        TextView city = view.findViewById(R.id.cityValues);
        TextView sellRent = view.findViewById(R.id.sellRentValue);
        TextView phone = view.findViewById(R.id.phoneValue);
        TextView suburb = view.findViewById(R.id.suburbValues);
        TextView mortgageOneLabel = view.findViewById(R.id.mortgageOneLabel);
        TextView mortgageOneValue = view.findViewById(R.id.mortgageOneValue);
        TextView mortgageTwoLabel = view.findViewById(R.id.mortgageTwoLabel);
        TextView mortgageTwoValue = view.findViewById(R.id.mortgageTwoValue);
        TextView mortgageThreeValue = view.findViewById(R.id.mortgageThreeValue);
        TextView mortgageThreeLabel = view.findViewById(R.id.mortgageThreeLabel);
        TextView securityDeposit = view.findViewById(R.id.securityDepositValue);
        like = view.findViewById(R.id.like);
        TextView likeLabel = view.findViewById(R.id.likeLabel);


        assert getArguments() != null;
        ArrayList<String> items = getArguments().getStringArrayList("property");


        assert items != null;
        property.setText(items.get(0));
        price.setText(String.format("%s RTGS",items.get(1)));
        bedrooms.setText(items.get(2));
        bathrooms.setText(items.get(3));
        area.setText(String.format("%s sqm",items.get(4)));
        address.setText(items.get(5));
        city.setText(items.get(6));
        phone.setText(items.get(7));
        suburb.setText(items.get(9));
        sellRent.setText(items.get(10));
        type.setText(items.get(11));
        reference.setText(items.get(12));
        String save_search = items.get(13);


        if ( items.get(10).equals("rent")){

            securityDeposit.setText(String.format("%s RTGS", (Double.parseDouble(items.get(1)) *2)));
            mortgageOneLabel.setText(R.string.monthly_rent);
            mortgageOneValue.setText(String.format("%s RTGS", items.get(1)));
            mortgageTwoLabel.setVisibility(View.GONE);
            mortgageTwoValue.setVisibility(View.GONE);
            mortgageThreeValue.setVisibility(View.GONE);
            mortgageThreeLabel.setVisibility(View.GONE);


        }
        else if(items.get(10).equals("buy")){

            securityDeposit.setText(String.format("%s RTGS", 0));
            mortgageOneValue.setText(String.format("%s RTGS", calMortgage(Double.parseDouble(items.get(1)), 15)));
            mortgageTwoValue.setText(String.format("%s RTGS", calMortgage(Double.parseDouble(items.get(1)),25)));
            mortgageThreeValue.setText(String.format("%s RTGS", calMortgage(Double.parseDouble(items.get(1)),30)));
        }

        if(save_search.equals("saved")){
            likeLabel.setText(R.string.unlike);
            action = "delete";
        }
        else if(save_search.equals("search")) {
           action = "insert";
        }

        like.setOnClickListener((v)->{

            saveProperty(items.get(12), action);
        });

        return view;
    }


    private double calMortgage(double principal, double term){

        double rate = 10.0/100.0/12.0;
        term = term * 12.0;
        double payment = (principal * rate) / (1 - Math.pow(1 + rate, -term));

        // round to two decimals
        payment = (double)Math.round(payment * 100.0) / 100.0;

        return payment;
    }

    private void saveProperty(String reference, String action) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_POST_SAVED,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject index = jsonArray.getJSONObject(0);
                        String code = index.getString("code");
                        String message = index.getString("message");

                        if (code.equals("Success")){

                            if(message.equals("deleted")){
                                like.setAlpha(0.5f);
                                Toast.makeText(getContext(), "This property has been removed from saved list", Toast.LENGTH_LONG).show();
                            }
                          else if(message.equals("updated")){
                                like.setAlpha(0.5f);
                                Toast.makeText(getContext(), "This property has been added to saved list", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (code.equals("Failed")){
                            Toast.makeText(getContext(), "Attempt has failed", Toast.LENGTH_LONG).show();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPref sharedPref = new SharedPref(getContext());

                params.put("username", sharedPref.getString("username", ""));
                params.put("reference", reference);
                params.put("action", action);

                return params;
            }
        };

        AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
