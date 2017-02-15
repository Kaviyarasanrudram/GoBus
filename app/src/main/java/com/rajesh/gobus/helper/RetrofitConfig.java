package com.rajesh.gobus.helper;

import com.google.gson.JsonElement;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public class RetrofitConfig {

    public interface get_bus_route {
        @GET("/bus_route_name.php")
        void retrive_bus_route(Callback<JsonElement> callback);
    }

    public interface get_bus_details {
        @GET("/bus_details.php")

        void retrive_bus_details(
                @Query("from") String bus_source,
                @Query("to") String bus_destination,
                Callback<JsonElement> callback);
    }
}
