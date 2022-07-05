package com.example.realeastateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.models.TrackModal;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder>{


    Context context;
    ArrayList<TrackModal> trackModals;

    public TrackAdapter(Context context, ArrayList<TrackModal> trackModals) {
        this.context = context;
        this.trackModals = trackModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_property_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.houseType.setText(trackModals.get(position).getHouseType());
        holder.approval.setText(trackModals.get(position).getApproval());
        holder.rentBuy.setText(trackModals.get(position).getRentBuy());
        holder.address.setText(trackModals.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return trackModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView address;
        private TextView rentBuy;
        private TextView approval;
        private TextView houseType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.addressValue);
            rentBuy = itemView.findViewById(R.id.rentBuyValue);
            approval = itemView.findViewById(R.id.approval);
            houseType = itemView.findViewById(R.id.houseTypeValue);
        }
    }
}
