package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ParkingApiService {
        @GET("maps/api/place/nearbysearch/json")
        Call<PlacesResponse> getNearbyParking(
                @Query("location") String location,
                @Query("radius") int radius,
                @Query("type") String type,
                @Query("key") String apiKey);
}
