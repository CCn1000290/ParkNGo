package com.example.myapplication;

import java.util.List;

public class PlacesResponse {
    private List<Result> results;

    // Getter and setter
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        private String name;
        private int capacity;
        private double PricePerHr;
        private String id;
        private Geometry geometry;




        // Getter and setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPlaceId() { return id;}

        public int getCapacity() { return capacity;}


        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public double getPricePerHr() {
            return PricePerHr;
        }

        public void setPricePerHr(double pricePerHr) {
            PricePerHr = pricePerHr;
        }
    }

    public static class Geometry {
        private Location location;

        // Getter and setter
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public static class Location {
        private double lat;
        private double lng;

        // Getter and setter
        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }

}
