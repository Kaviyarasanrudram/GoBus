package com.rajesh.gobus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonElement;
import com.rajesh.gobus.helper.AppConstants;
import com.rajesh.gobus.helper.RetrofitConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import info.hoang8f.widget.FButton;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomePageActivity extends AppCompatActivity {

    ArrayAdapter<String> from_adapter;
    ArrayAdapter<String> to_adapter;
    static MaterialSpinner from_spinner;
    static MaterialSpinner to_spinner;
    MaterialDialog material_progress;
    RestAdapter adapter;
    RetrofitConfig.get_bus_route bus_details;
    ArrayList<String> place_details = new ArrayList<>();
    FButton bus_details_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        from_spinner = (MaterialSpinner) findViewById(R.id.from);
        to_spinner = (MaterialSpinner) findViewById(R.id.to);
        bus_details_btn = (FButton) findViewById(R.id.busDetails);
        material_progress = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content("Loading...")
                .widgetColor(getResources().getColor(R.color.colorPrimaryDark))
                .cancelable(true)
                .build();

        retrieveBus();

        bus_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from_spinner_item = from_spinner.getSelectedItem().toString();
                String to_spinner_item = to_spinner.getSelectedItem().toString();
                if (from_spinner_item.equals("Source") || to_spinner_item.equals("Destination")) {

                    new MaterialDialog.Builder(HomePageActivity.this)
                            .title("Warning")
                            .content("Please select the source and destination")
                            .positiveText("OK")
                            .titleColorRes(R.color.colorPrimaryDark)
                            .cancelable(false)
                            .show();
                } else if (from_spinner_item.equals(to_spinner_item)) {

                    new MaterialDialog.Builder(HomePageActivity.this)
                            .title("Warning")
                            .content("Destination should not be same as source")
                            .positiveText("OK")
                            .titleColorRes(R.color.colorPrimaryDark)
                            .cancelable(false)
                            .show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), BusDetailsActivity.class);
                    intent.putExtra("from_address", from_spinner_item);
                    intent.putExtra("to_address", to_spinner_item);
                    startActivity(intent);
                }
            }
        });
    }


    public void retrieveBus() {

        material_progress.show();
        adapter = new RestAdapter.Builder()
                .setEndpoint(AppConstants.root_url)
                .build();

        bus_details = adapter.create(RetrofitConfig.get_bus_route.class);
        bus_details.retrive_bus_route(
                new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement result, Response response) {

                        String myResponse = result.toString();
                        Log.d("response", "" + myResponse);
                        try {
                            JSONObject jObj = new JSONObject(myResponse);
                            String success = jObj.getString("success");
                            //Log.d("success_message", success);
                            if (success.equals("1")) {

                                JSONArray jsonArray = jObj.getJSONArray("bus_route_name");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String bus_route_name = jsonObject.getString("bus_route");
                                    Log.d("route_details", bus_route_name);
                                    place_details.add(bus_route_name);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "No details found", Toast.LENGTH_LONG).show();
                            }
                            Log.d("array_list", "" + place_details);

                            from_adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, place_details);
                            from_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            from_spinner.setAdapter(from_adapter);

                            to_adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, place_details);
                            to_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            to_spinner.setAdapter(to_adapter);

                        } catch (JSONException e) {
                            Log.d("exception_details", e.toString());
                            material_progress.hide();
                        }
                        material_progress.hide();
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        material_progress.hide();
                        Log.d("Failure", error.toString());

                        String check_internet_error = error.toString();
                        if (check_internet_error.equals("retrofit.RetrofitError: failed to connect to kavihari.esy.es/31.220.20.245 (port 80) after 15000ms")) {
                            retrieveBus();
                        } else {
                            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }
}
