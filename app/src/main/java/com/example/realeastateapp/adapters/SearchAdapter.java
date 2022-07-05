package com.example.realeastateapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<HousesModal> houses;
    public static ArrayList<HousesModal> houseFilter;


    public SearchAdapter() {
    }

    public SearchAdapter(Context context, ArrayList<HousesModal> houses) {
        this.context = context;
        this.houses = houses;
        houseFilter = houses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return houseFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.cost.setText(String.format("%s RTGS",houseFilter.get(position).getPrice()));
        holder.rent_buy.setText(houseFilter.get(position).getSellRent());
        holder.suburb.setText(String.format("%s %s",houseFilter.get(position).getAddress(), houseFilter.get(position).getCity()));
        holder.houseType.setText(houseFilter.get(position).getHouseType());
        holder.bathrooms.setText(houseFilter.get(position).getBathrooms());
        holder.bedrooms.setText(houseFilter.get(position).getBedrooms());
// ADD CALL AND SMS FUNCTIONALITY
        ArrayList<String> items = new ArrayList<>(Arrays.asList(
                houseFilter.get(position).getHouseDescription(),
                houseFilter.get(position).getPrice(),
                houseFilter.get(position).getBedrooms(),
                houseFilter.get(position).getBathrooms(),
                houseFilter.get(position).getArea(),
                houseFilter.get(position).getAddress(),
                houseFilter.get(position).getCity(),
                houseFilter.get(position).getNumber(),
                houseFilter.get(position).getLocationType(),
                houseFilter.get(position).getSuburb(),
                houseFilter.get(position).getSellRent(),
                houseFilter.get(position).getHouseType(),
                houses.get(position).getReference(),
                "search"));


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

        });


    }






    class MyViewHolder extends RecyclerView.ViewHolder{

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    houseFilter = houses;
                } else {
                    ArrayList<HousesModal> filteredList = new ArrayList<>();
                    for (HousesModal row : houses) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSellRent().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getPrice().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getArea().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCity().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getSuburb().toLowerCase().contains(charString.toLowerCase())||
                                row.getHouseType().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBathrooms().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBedrooms().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    houseFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = houseFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                houseFilter = (ArrayList<HousesModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
