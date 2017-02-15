package com.rajesh.gobus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajesh.gobus.R;

import java.util.ArrayList;

public class BusDetailsAdapter extends RecyclerView.Adapter<BusDetailsAdapter.ItemViewHolder> {

    private final Activity context;
    private final ArrayList<String> bus_number_array_list;
    private final ArrayList<String> bus_route_number_array_list;
    private final ArrayList<String> bus_type_txt_array_list;
    private final ArrayList<String> total_travel_time_array_list;
    private final ArrayList<String> bus_fare_array_list;
    private final ArrayList<String> bus_starting_time_array_list;
    private final ArrayList<String> bus_reaching_time_array_list;
    private final ArrayList<String> bus_source_array_list;
    private final ArrayList<String> bus_destination_array_list;
    private final ArrayList<String> bus_route_desc_array_list;

    public BusDetailsAdapter(Activity cartActivity, ArrayList bus_number_array, ArrayList bus_route_number_array, ArrayList bus_type_txt_array,
                             ArrayList total_travel_time_array, ArrayList bus_fare_array, ArrayList bus_starting_time_array,
                             ArrayList bus_reaching_time_array, ArrayList bus_source_array, ArrayList bus_destination_array, ArrayList bus_route_desc_array) {

        this.context = cartActivity;
        this.bus_number_array_list = bus_number_array;
        this.bus_route_number_array_list = bus_route_number_array;
        this.bus_type_txt_array_list = bus_type_txt_array;
        this.total_travel_time_array_list = total_travel_time_array;
        this.bus_fare_array_list = bus_fare_array;
        this.bus_starting_time_array_list = bus_starting_time_array;
        this.bus_reaching_time_array_list = bus_reaching_time_array;
        this.bus_source_array_list = bus_source_array;
        this.bus_destination_array_list = bus_destination_array;
        this.bus_route_desc_array_list = bus_route_desc_array;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView bus_route_number_txt, bus_type_txt, total_travel_time_txt, bus_fare_txt,
                bus_starting_time_txt, bus_reaching_time_txt, bus_source_txt, bus_destination_txt, bus_route_desc_txt;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            //bus_number_txt = (TextView) itemView.findViewById(R.id.id_bus_number);
            bus_route_number_txt = (TextView) itemView.findViewById(R.id.id_bus_route_number);
            bus_type_txt = (TextView) itemView.findViewById(R.id.id_bus_type);
            total_travel_time_txt = (TextView) itemView.findViewById(R.id.id_total_travel_time);
            bus_fare_txt = (TextView) itemView.findViewById(R.id.id_bus_fare);
            bus_starting_time_txt = (TextView) itemView.findViewById(R.id.id_bus_starting_time);
            bus_reaching_time_txt = (TextView) itemView.findViewById(R.id.id_bus_reaching_time);
            bus_source_txt = (TextView) itemView.findViewById(R.id.id_bus_source);
            bus_destination_txt = (TextView) itemView.findViewById(R.id.id_bus_destination);
            bus_route_desc_txt = (TextView) itemView.findViewById(R.id.id_bus_route_desc);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bus_details_adapter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

       // holder.bus_number_txt.setText(bus_number_array_list.get(position));
        holder.bus_route_number_txt.setText(bus_route_number_array_list.get(position));
        holder.bus_type_txt.setText(bus_type_txt_array_list.get(position));
        holder.total_travel_time_txt.setText(total_travel_time_array_list.get(position));
        holder.bus_fare_txt.setText(bus_fare_array_list.get(position));
        holder.bus_starting_time_txt.setText(bus_starting_time_array_list.get(position));
        holder.bus_reaching_time_txt.setText(bus_reaching_time_array_list.get(position));
        holder.bus_source_txt.setText(bus_source_array_list.get(position));
        holder.bus_destination_txt.setText(bus_destination_array_list.get(position));
        holder.bus_route_desc_txt.setText(bus_route_desc_array_list.get(position));

    }

    @Override
    public int getItemCount() {
        return bus_number_array_list.size();
    }

}