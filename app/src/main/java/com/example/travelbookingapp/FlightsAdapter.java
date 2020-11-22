package com.example.travelbookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ProductViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Flight> flightList;

    //getting the context and product list with constructor
    public FlightsAdapter(Context mCtx, List<Flight> productList) {
        this.mCtx = mCtx;
        this.flightList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_flightresults_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Flight flight = flightList.get(position);

        //binding the data with the viewholder views
        holder.AirlineName.setText(flight.getAirlinename());
        //holder.DepartureTime.setText(flight.getDuration());
        //holder.AirlineName.setText(String.valueOf(flight.getAirlinename()));
        //holder.Duration.setText(String.valueOf(flight.getDuration()));
        holder.DepartureTime.setText(String.valueOf(flight.getDeparturetime()));
        holder.ArrivalTime.setText(String.valueOf(flight.getArrivaltime()));
        holder.textViewPrice.setText(String.valueOf(flight.getPrice()));

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(flight.getImage()));

    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView AirlineName, Duration, ArrivalTime, DepartureTime, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            AirlineName = itemView.findViewById(R.id.AirlineName);
            //Duration = itemView.findViewById(R.id.Du)
            DepartureTime = itemView.findViewById(R.id.DepartureTime);
            ArrivalTime = itemView.findViewById(R.id.ArrivalTime);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}