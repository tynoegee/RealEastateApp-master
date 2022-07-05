package com.example.realeastateapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.fragments.FullPropertyViewFragment;
import com.example.realeastateapp.models.HousesModal;

import java.util.ArrayList;
import java.util.Arrays;


public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HousesModal> houses;

    public SavedAdapter() {
    }

    public SavedAdapter(Context context, ArrayList<HousesModal> houses) {
        this.context = context;
        this.houses = houses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cost.setText(String.format("%s RTGS",houses.get(position).getPrice()));
        holder.rent_buy.setText(houses.get(position).getSellRent());
        holder.suburb.setText(String.format("%s %s",houses.get(position).getAddress(), houses.get(position).getCity()));
        holder.houseType.setText(houses.get(position).getHouseType());
        holder.bathrooms.setText(houses.get(position).getBathrooms());
        holder.bedrooms.setText(houses.get(position).getBedrooms());
        ArrayList<String> items = new ArrayList<>(Arrays.asList(
                houses.get(position).getHouseDescription(),
                houses.get(position).getPrice(),
                houses.get(position).getBedrooms(),
                houses.get(position).getBathrooms(),
                houses.get(position).getArea(),
                houses.get(position).getAddress(),
                houses.get(position).getCity(),
                houses.get(position).getNumber(),
                houses.get(position).getLocationType(),
                houses.get(position).getSuburb(),
                houses.get(position).getSellRent(),
                houses.get(position).getHouseType(),
                houses.get(position).getReference(),
                "saved"));


        holder.propertyCardView.setOnClickListener((v)->{

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("property", items);

            Fragment fragment = new FullPropertyViewFragment();
            fragment.setArguments(bundle);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frags, fragment)
                    .addToBackStack(null)
                    .commit();
            houses.clear();

        });
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cost;
        private TextView rent_buy;
        private TextView suburb;
        private TextView houseType;
        private TextView bedrooms;
        private TextView bathrooms;
        private ImageView phone;
        private ImageView sms;
        private CardView propertyCardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cost = itemView.findViewById(R.id.cost);
            rent_buy = itemView.findViewById(R.id.rent_buy);
            suburb = itemView.findViewById(R.id.suburb);
            houseType = itemView.findViewById(R.id.houseType);
            bedrooms = itemView.findViewById(R.id.roomQuantity);
            bathrooms = itemView.findViewById(R.id.restRooms);
            phone = itemView.findViewById(R.id.callSender);
            sms = itemView.findViewById(R.id.emailSeller);
            propertyCardView = itemView.findViewById(R.id.propertyCardView);
        }
    }
}
