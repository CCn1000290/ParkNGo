package com.example.myapplication;

public class ParkingSpot {
    private String id;
    private String name;
    private int capacity;
    private double pricePerHour;

    private String address;
    private double distance;
    private boolean isOccupied;

        public ParkingSpot(String id, String name, int capacity, double pricePerHour,double latitude, double longitude, String address, double distance, boolean isOccupied) {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
            this.pricePerHour = pricePerHour;
            this.address = address;
            this.distance = distance;
            this.isOccupied = isOccupied;
        }

    public ParkingSpot(String id, String name, int capacity, double latitude, double longitude, double pricePerHour) {
    }

    // Getters and setters
        public String getId() {
            return id;
        }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}