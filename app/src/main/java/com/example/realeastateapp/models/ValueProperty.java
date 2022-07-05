package com.example.realeastateapp.models;

public class ValueProperty {


    private String phone_number;
    private String houseType;
    private String sellRent;
    private String numRooms;
    private String numRestrooms;
    private String suburb;
    private String amount;
    private String locationType;
    private String description;
    private String address;
    private String city;
    private String houseSize;
    private int pic;

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getSellRent() {
        return sellRent;
    }

    public void setSellRent(String sellRent) {
        this.sellRent = sellRent;
    }

    public String getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(String numRooms) {
        this.numRooms = numRooms;
    }

    public String getNumRestrooms() {
        return numRestrooms;
    }

    public void setNumRestrooms(String numRestrooms) {
        this.numRestrooms = numRestrooms;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    /*
     * calculate the value of the house here
     * en send it back
     */


    public int calHomeSell(int bedrooms, int bathrooms, String type, int sqm) {


        int priceBedroom = 0;
        int priceBathrooms = 0;
        int priceType = 0;

        if (bedrooms < 2)
            priceBedroom = 300;
        else if (bedrooms > 2 && bedrooms < 5)
            priceBedroom = 500;
        else if (bedrooms > 5)
            priceBedroom = 800;

        if (bathrooms < 1)
            priceBathrooms = 100;
        else if (bathrooms > 2 && bathrooms < 4)
            priceBathrooms = 200;
        else if (bathrooms > 4)
            priceBathrooms = 300;

        switch (type) {
            case "Low density":
                priceType = 80000 + calSqmSell(sqm);
                break;
            case "Medium density":
                priceType = 50000 + calSqmSell(sqm);
                break;
            case "High density":
                priceType = 20000 + calSqmSell(sqm);
                break;
        }

        return (priceBathrooms + priceBedroom + priceType);

    }

    private int calSqmSell(int sqm) {
        int calSqm = 0;

        if (sqm < 50) {
            calSqm = 500;
        } else if (sqm > 50 && sqm < 100) {
            calSqm = 1000;
        } else if (sqm > 100 && sqm < 200) {
            calSqm = 2000;
        } else if (sqm > 200) {
            calSqm = 3000;
        }

        return calSqm;
    }

    public int calHomeRent(int bedrooms, int bathrooms, String type, int sqm) {

        int priceBedroom = 0;
        int priceBathrooms = 0;
        int priceType = 0;

        if (bedrooms < 2)
            priceBedroom = 30;
        else if (bedrooms > 2 && bedrooms < 5)
            priceBedroom = 50;
        else if (bedrooms > 5)
            priceBedroom = 80;

        if (bathrooms < 1)
            priceBathrooms = 10;
        else if (bathrooms > 2 && bathrooms < 4)
            priceBathrooms = 20;
        else if (bathrooms > 4)
            priceBathrooms = 30;

        switch (type) {
            case "Low density":
                priceType = 800 + calSqmRent(sqm);
                break;
            case "Medium density":
                priceType = 500 + calSqmRent(sqm);
                break;
            case "High density":
                priceType = 200 + calSqmRent(sqm);
                break;
        }

        return (priceBathrooms + priceBedroom + priceType);
    }

    private int calSqmRent(int sqm) {
        int calSqm = 0;

        if (sqm < 50) {
            calSqm = 50;
        } else if (sqm > 50 && sqm < 100) {
            calSqm = 100;
        } else if (sqm > 100 && sqm < 200) {
            calSqm = 200;
        } else if (sqm > 200) {
            calSqm = 300;
        }

        return calSqm;
    }


}
