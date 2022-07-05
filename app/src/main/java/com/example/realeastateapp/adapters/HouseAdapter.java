package com.example.realeastateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.models.HousesModal;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyViewHolder> {

    Context context;
    ArrayList<HousesModal> housesModals;

    public HouseAdapter(Context context, ArrayList<HousesModal> housesModals) {
        this.context = context;
        this.housesModals = housesModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return housesModals.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.priceValue.setText(housesModals.get(position).getPrice());
        holder.bedroomValue.setText(housesModals.get(position).getBedrooms());
        holder.bathroomValue.setText(housesModals.get(position).getBathrooms());
        holder.addressValue.setText(housesModals.get(position).getAddress());
        holder.cityValue.setText(housesModals.get(position).getCity());
        holder.rent_buyValue.setText(housesModals.get(position).getSellRent());

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView priceValue;
        private TextView bedroomValue;
        private TextView bathroomValue;
        private TextView addressValue;
        private TextView cityValue;
        private TextView rent_buyValue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            priceValue = itemView.findViewById(R.id.pricValues);
            bedroomValue = itemView.findViewById(R.id.bedValues);
            bathroomValue = itemView.findViewById(R.id.restValues);
            addressValue = itemView.findViewById(R.id.addValues);
            cityValue = itemView.findViewById(R.id.citValues);
            rent_buyValue = itemView.findViewById(R.id.srValues);

        }
    }
}
