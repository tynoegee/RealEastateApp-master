package com.example.realeastateapp.models;

public class TrackModal {

    private String address;
    private String rentBuy;
    private String approval;
    private String houseType;

    public TrackModal(String address, String rentBuy, String approval, String houseType) {
        this.address = address;
        this.rentBuy = rentBuy;
        this.approval = approval;
        this.houseType = houseType;
    }

    public String getAddress() {
        return address;
    }

    public String getRentBuy() {
        return rentBuy;
    }

    public String getApproval() {
        return approval;
    }

    public String getHouseType() {
        return houseType;
    }
}
