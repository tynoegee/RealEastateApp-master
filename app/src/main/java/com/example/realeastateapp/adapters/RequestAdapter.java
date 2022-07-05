package com.example.realeastateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.models.UserModal;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<UserModal> userModals;

    public RequestAdapter(Context context, ArrayList<UserModal> userModals) {
        this.context = context;
        this.userModals = userModals;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approve_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.regnum.setText(userModals.get(position).getLabel());
        holder.ref.setText(userModals.get(position).getValue());
        holder.status.setText(userModals.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return userModals.size();
    }
    public void removeItem(int position) {
        userModals.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }



    public void restoreItem(UserModal item, int position) {
        userModals.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView regnum;
        private TextView ref;
        private TextView status;
        public LinearLayout viewForeground ;
        public RelativeLayout viewBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            regnum = itemView.findViewById(R.id.regnum);
            ref = itemView.findViewById(R.id.refValue);
            status = itemView.findViewById(R.id.statusValue);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
        }
    }
}
