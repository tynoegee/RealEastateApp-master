package com.example.realeastateapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.realeastateapp.models.UserModal;
import com.example.realeastateapp.utilities.ApiUrl;
import com.example.realeastateapp.utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.text.TextUtils.isEmpty;



public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
    }
private  EditText fname;
    private EditText lname;
    private  EditText city;
    private EditText address;
    private  EditText phone_number;
    private EditText whatsapp_number;
    private EditText email;
    private EditText company_name;
    private UserModal userModal = new UserModal();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Button submit = view.findViewById(R.id.submitDetails);

        fname = view.findViewById(R.id.fname);
        lname =view.findViewById(R.id.lname);
        city = view.findViewById(R.id.city);
        address = view.findViewById(R.id.address);
        phone_number = view.findViewById(R.id.phone_number);
        whatsapp_number = view.findViewById(R.id.whatsapp_number);
        email = view.findViewById(R.id.email);
        company_name =view.findViewById(R.id.company);
        Spinner services = view.findViewById(R.id.service);

        submit.setOnClickListener((v)->{
            validate();
            String strFname = fname.getText().toString().trim();
            String strLname = lname.getText().toString().trim();
            String strCity = city.getText().toString().trim();
            String strAddress = address.getText().toString().trim();
            String strPhone = phone_number.getText().toString().trim();
            String strWhatsapp = whatsapp_number.getText().toString().trim();
            String strEmail = email.getText().toString().trim();
            String strCompany = company_name.getText().toString().trim();
            String strServices = services.getSelectedItem().toString().trim();

            userModal.setFname(strFname);
            userModal.setLname(strLname);
            userModal.setEmail(strEmail);
            userModal.setCity(strCity);
            userModal.setCompany(strCompany);
            userModal.setAddress(strAddress);
            userModal.setWhatsappNum(strWhatsapp);
            userModal.setPhoneNum(strPhone);
            userModal.setService(strServices);


            postDetails(view);
        });
        return view;
    }

    private void postDetails(View view){
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, ApiUrl.URL_UPDATE_DETAILS, response -> {

            try {

                // gets response from php file for success or failure
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");

                if (code.equals("success")) {
                    Intent intent = new Intent(view.getContext(), ViewProfileFragment.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else if (code.equals("failed")) {

                    Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {

            if (error instanceof TimeoutError) {
                Toast.makeText(view.getContext(), "Attempt has timed out. Please try again.",
                        Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(view.getContext(), "Network Error", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(view.getContext(), "Server is down", Toast.LENGTH_LONG).show();

            }
            error.printStackTrace();

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {

                // stores the login details using key pair system
                Map<String, String> params = new HashMap<>();
                params.put("fname", userModal.getFname() );
                params.put("lname", userModal.getLname());
                params.put("city", userModal.getCity() );
                params.put("address", userModal.getAddress() );
                params.put("whatsapp", userModal.getWhatsappNum());
                params.put("service", userModal.getService());
                params.put("phone", userModal.getPhoneNum());
                params.put("email", userModal.getEmail() );
                params.put("company", userModal.getCompany() );

                return params;

            }
        };

        AppSingleton.getInstance(view.getContext()).addToRequestQueue(myStringRequest);
    }

    private void validate(){
        String strFname = fname.getText().toString().trim();
        String strLname = lname.getText().toString().trim();
        String strCity = city.getText().toString().trim();
        String strAddress = address.getText().toString().trim();
        String strPhone = phone_number.getText().toString().trim();
        String strWhatsapp = whatsapp_number.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strCompany = company_name.getText().toString().trim();

        if (isEmpty(strFname)){
            fname.setError("Please enter your First name");
        }
        if (isEmpty(strLname)){
            lname.setError("Please enter your Last name");
        }
        if (isEmpty(strEmail)){
            email.setError("Please enter your Email Address");
        }
        if (isEmpty(strCompany)){
            company_name.setError("Please enter your Company name");
        }

        if (isEmpty(strPhone)){
            phone_number.setError("Please enter your Phone number");
        }
        if (isEmpty(strWhatsapp)){
           whatsapp_number.setError("Please enter your Whatsapp number");
        }
        if (isEmpty(strCity)){
            city.setError("Please enter the City you are in");
        }
        if (isEmpty(strAddress)){
            address.setError("Please enter your Address");
        }


    }
}
