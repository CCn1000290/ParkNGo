package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ParkingSpotAdapter extends BaseAdapter {
    private Context context;
    private List<ParkingSpot> parkingSpots;

    public ParkingSpotAdapter(Context context, List<ParkingSpot> parkingSpots) {
        this.context = context;
        this.parkingSpots = parkingSpots;
    }

    @Override
    public int getCount() {
        return parkingSpots.size();
    }

    @Override
    public Object getItem(int position) {
        return parkingSpots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView capacityTextView;
        TextView pricePerHourTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parking_spot_item, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.parkingSpotName);
            holder.addressTextView = convertView.findViewById(R.id.parkingSpotAddress);
            holder.capacityTextView = convertView.findViewById(R.id.parkingSpotCapacity);
            holder.pricePerHourTextView = convertView.findViewById(R.id.parkingSpotPricePerHour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParkingSpot parkingSpot = parkingSpots.get(position);

        holder.nameTextView.setText(parkingSpot.getName());
        // Assuming ParkingSpot model has an getAddress() method. If not, adjust accordingly.
        holder.addressTextView.setText(parkingSpot.getAddress());
        holder.capacityTextView.setText("Capacity: " + parkingSpot.getCapacity());
        holder.pricePerHourTextView.setText("Price/Hour: $" + parkingSpot.getPricePerHour());

        return convertView;
    }
    public void clear() {
        parkingSpots.clear();
    }

    public void addAll(List<ParkingSpot> newParkingSpots) {
        parkingSpots.addAll(newParkingSpots);
    }

}
