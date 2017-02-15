package com.rajesh.gobus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonElement;
import com.rajesh.gobus.adapter.BusDetailsAdapter;
import com.rajesh.gobus.helper.AppConstants;
import com.rajesh.gobus.helper.RetrofitConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BusDetailsActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;

    RestAdapter adapter;
    RetrofitConfig.get_bus_details retrive_bus_data;
    BusDetailsAdapter busDetailsAdapter;
    RecyclerView bus_list;
    MaterialDialog material_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);
        bus_list = (RecyclerView) findViewById(R.id.bus_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        bus_list.setLayoutManager(layoutManager);

        material_progress = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content("Loading...")
                .widgetColor(getResources().getColor(R.color.colorPrimaryDark))
                .cancelable(true)
                .build();

        Intent intent = getIntent();
        String from_address = intent.getStringExtra("from_address");
        String to_address = intent.getStringExtra("to_address");

        Log.d("address", "" + from_address + "---" + to_address);
        retrive_bus_details(from_address, to_address);

        // Capture button clicks


    }
    public void buttonClick(View view){
        Intent mintent = new Intent(BusDetailsActivity.this, MapsActivity.class);
        startActivity(mintent);
    }

    public void retrive_bus_details(String from, String to) {

        material_progress.show();

        adapter = new RestAdapter.Builder()
                .setEndpoint(AppConstants.root_url)
                .build();

        retrive_bus_data = adapter.create(RetrofitConfig.get_bus_details.class);
        retrive_bus_data.retrive_bus_details(from, to,
                new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement result, Response response) {
                        String myResponse = result.toString();
                        Log.d("response", "" + myResponse);
                        try {
                            JSONObject jObj = new JSONObject(result.toString());
                            int success = jObj.getInt("success");
                            if (success == 1) {

                                Log.d("success_details", "success_details");
                                JSONArray jsonArray = jObj.getJSONArray("bus_list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String bus_number_string = jsonObject.getString("bus_number");
                                    String bus_route_number_string = jsonObject.getString("bus_route_number");
                                    String bus_type_string = jsonObject.getString("bus_type");
                                    String bus_fare_string = jsonObject.getString("bus_fare");
                                    String bus_source_string = jsonObject.getString("bus_source");
                                    String bus_destination_string = jsonObject.getString("bus_destination");
                                    String total_travel_time_string = jsonObject.getString("total_travel_time");
                                    String bus_starting_time_string = jsonObject.getString("bus_starting_time");
                                    String bus_reaching_time_string = jsonObject.getString("bus_reaching_time");
                                    String bus_route_desc_string = jsonObject.getString("bus_route_desc");



                                    AppConstants.bus_number_list.add(bus_number_string);
                                    AppConstants.bus_route_number_list.add(bus_route_number_string);
                                    AppConstants.bus_type_list.add(bus_type_string);
                                    AppConstants.bus_fare_list.add(bus_fare_string);
                                    AppConstants.bus_source_list.add(bus_source_string);
                                    AppConstants.bus_destination_list.add(bus_destination_string);
                                    AppConstants.total_travel_time_list.add(total_travel_time_string);
                                    AppConstants.bus_starting_time_list.add(bus_starting_time_string);
                                    AppConstants.bus_reaching_time_list.add(bus_reaching_time_string);
                                    AppConstants.bus_route_desc_list.add(bus_route_desc_string);


                                    if (jsonArray.length() - 1 == i)


                                        busDetailsAdapter = new BusDetailsAdapter(BusDetailsActivity.this, AppConstants.bus_number_list, AppConstants.bus_route_number_list,
                                                AppConstants.bus_type_list, AppConstants.total_travel_time_list, AppConstants.bus_fare_list,
                                                AppConstants.bus_source_list, AppConstants.bus_destination_list,
                                                AppConstants.bus_starting_time_list, AppConstants.bus_reaching_time_list, AppConstants.bus_route_desc_list);

                                    bus_list.setAdapter(busDetailsAdapter);
                                }


                            } else {
                                new MaterialDialog.Builder(BusDetailsActivity.this)
                                        .title("Error")
                                        .content("No details available")
                                        .positiveText("OK")
                                        .titleColorRes(R.color.colorPrimaryDark)
                                        .cancelable(false)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                                            }
                                        })
                                        .show();
                            }
                        } catch (JSONException e) {
                            Log.d("exception", e.toString());
                        }
                        material_progress.hide();
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        material_progress.hide();
                        Toast.makeText(getApplicationContext(), "Check the internet connection", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }





}

