package com.example.travelbookingapp;

public class Flight {
    private String id;
    private String duration;
    private String airlinename;
    private String departuretime;
    private String arrivaltime;
    private String price;


    public Flight(String id, String duration, String airlinename, String departuretime, String arrivaltime, String price) {
        this.id = id;
        this.duration = duration;
        this.airlinename = airlinename;
        this.departuretime = departuretime;
        this.arrivaltime = arrivaltime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public String getAirlinename(){
        return airlinename;
    }

    public String getDeparturetime(){
        return departuretime;
    }

    public String getArrivaltime(){
        return arrivaltime;
    }

    public String getPrice(){
        return price;
    }


}