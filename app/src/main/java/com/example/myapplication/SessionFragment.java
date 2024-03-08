package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SessionFragment extends Fragment {
    private EditText locationSearchInput;
    private ParkingApiService service;

    private List<ParkingSpot> parkingSpots = new ArrayList<>();
    private ListView parkingListView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public SessionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeRetrofit(); // Initialize Retrofit here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        setupView(view);
        fetchNearbyParkingSpots(52.95, -1.150); // Example coordinates
        return view;


    }

    private void setupView(View view) {
        locationSearchInput = view.findViewById(R.id.location_search_input);
        parkingListView = view.findViewById(R.id.parkingListView);
        ParkingSpotAdapter adapter = new ParkingSpotAdapter(getContext(), parkingSpots);
        parkingListView.setAdapter(adapter);

        parkingListView.setOnItemClickListener((parent, view1, position, id) -> {
            ParkingSpot selectedSpot = parkingSpots.get(position);
            showBookingDialog(selectedSpot);
        });

        ImageButton cameraButton = view.findViewById(R.id.button_QR);
        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        locationSearchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    private void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ParkingApiService.class);
    }

    private void fetchNearbyParkingSpots(double latitude, double longitude) {
        String location = latitude + "," + longitude;
        int radius = 1000; // Example radius in meters
        String type = "parking";
        String apiKey = "AIzaSyAFoUtgLJeIuCudNc135gkVK-yEgRA0rZI";

        // Assuming getNearbyParking correctly calls the Google Places API (or your backend)
        Call<PlacesResponse> call = service.getNearbyParking(location, radius, type, apiKey);


           call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ParkingSpot> spots = convertPlacesToParkingSpots(response.body());
                        updateListView(spots);
                    } else {
                        Toast.makeText(getContext(), "API error", Toast.LENGTH_LONG).show();
                    }
                }
               @Override
               public void onFailure(Call<PlacesResponse> call, Throwable t) {
                   Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
               }
        });
    }

    public void updateListView(List<ParkingSpot> parkingSpots) {
        ParkingSpotAdapter adapter = (ParkingSpotAdapter) parkingListView.getAdapter();
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(parkingSpots);
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ParkingSpotAdapter(getContext(), parkingSpots);
            parkingListView.setAdapter(adapter);
        }
    }
    private List<ParkingSpot> convertPlacesToParkingSpots(PlacesResponse response) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (PlacesResponse.Result result : response.getResults()) {
            String id = result.getPlaceId();
            String name = result.getName();
            int capacity = result.getCapacity();
            double pricePerHour = result.getPricePerHr();
            double latitude = result.getGeometry().getLocation().getLat();
            double longitude = result.getGeometry().getLocation().getLng();

            // Since the Places API does not provide parking capacity and price per hour, use placeholders or additional logic
            parkingSpots.add(new ParkingSpot(id, name, capacity, latitude, longitude, pricePerHour));
        }
        return parkingSpots;
    }

    private void showBookingDialog(ParkingSpot parkingSpot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(parkingSpot.getName());
        builder.setMessage("Capacity: " + parkingSpot.getCapacity() + "\nPrice/hr: £" + parkingSpot.getPricePerHour());
        builder.setPositiveButton("Book", (dialog, which) -> {
            // Handle booking confirmation here
            Toast.makeText(getContext(), "Booking confirmed for " + parkingSpot.getName(), Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void performSearch(String query) {
        // Assuming 'service' is your Retrofit interface instance
        // and it has been initialized correctly in 'initializeRetrofit'

        double latitude = 52.95; // Example latitude
        double longitude = -1.150; // Example longitude
        int radius = 1000; // Search radius in meters
        String apiKey = "AIzaSyAFoUtgLJeIuCudNc135gkVK-yEgRA0rZI"; // Replace with your actual API key
        String location = latitude + "," + longitude;
        String type = "parking"; // Assuming you are looking for parking spots

        Call<PlacesResponse> call = service.getNearbyParking(location, radius, type, apiKey);

        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Assuming PlacesResponse contains a list of results
                    List<ParkingSpot> newParkingSpots = convertPlacesToParkingSpots(response.body());
                    updateListView(newParkingSpots);
                } else {
                    // Handle API error
                    String errorMessage = "API error: Failed to retrieve data";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += " - " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                // Handle network failure
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
