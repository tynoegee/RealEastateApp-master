package com.example.realeastateapp.models;

public class HousesModal {

    private String houseDescription;
    private String price;
    private String bedrooms;
    private String bathrooms;
    private String area;
    private String address;
    private String city;
    private String number;
    private String locationType;
    private String suburb;
    private String sellRent;
    private String houseType;
    private String reference;


    public HousesModal(String price, String bedrooms, String bathrooms, String address, String city, String sellRent) {
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;
        this.city = city;
        this.sellRent = sellRent;
    }

    public HousesModal(String houseDescription, String price, String bedrooms, String bathrooms, String area, String address, String city, String number, String locationType, String suburb, String sellRent, String houseType, String reference) {
        this.houseDescription = houseDescription;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.area = area;
        this.address = address;
        this.city = city;
        this.number = number;
        this.locationType = locationType;
        this.suburb = suburb;
        this.sellRent = sellRent;
        this.houseType = houseType;
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public String getHouseType() {
        return houseType;
    }


    public String getHouseDescription() {
        return houseDescription;
    }

    public String getPrice() {
        return price;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getSellRent() {
        return sellRent;
    }
}
