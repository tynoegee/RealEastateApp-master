package com.example.realeastateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realeastateapp.R;
import com.example.realeastateapp.models.LoginTimes;

import java.util.ArrayList;


public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {

    private ArrayList<LoginTimes> loginTimes;
    private Context context;

    public LogAdapter(Context context, ArrayList<LoginTimes> loginTimes) {
        this.loginTimes = loginTimes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return loginTimes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(loginTimes.get(position).getUsername());
        holder.login.setText(loginTimes.get(position).getLogin());
        holder.logout.setText(loginTimes.get(position).getLogout());
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView login;
        private TextView logout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            logout =itemView.findViewById(R.id.logout_time);
            login = itemView.findViewById(R.id.login_time);
            username = itemView.findViewById(R.id.log_id);
        }
    }
}
