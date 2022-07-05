package com.example.realeastateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.models.UserModal;

import java.util.ArrayList;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserModal> userModal;

    public ProfileAdapter(Context context, ArrayList<UserModal> userModal) {
        this.context = context;
        this.userModal = userModal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.value.setText(userModal.get(position).getValue());
        holder.label.setText(userModal.get(position).getLabel());

    }

    @Override
    public int getItemCount() {
        return userModal.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView label;
        private TextView value;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            value = itemView.findViewById(R.id.value);
        }
    }
}
