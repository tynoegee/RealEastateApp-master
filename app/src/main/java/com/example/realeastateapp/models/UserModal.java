package com.example.realeastateapp.models;

public class UserModal {

    private String label;
    private String value;
    private String status;

    private String email;
    private String fname;
    private String lname;
    private String company;
    private String service;
    private String phoneNum;
    private String whatsappNum;
    private String city;
    private String address;



    public UserModal(String label, String value) {
        this.label = label;
        this.value = value;
    }
    public UserModal(String label, String value, String status) {
        this.label = label;
        this.value = value;
        this.status = status;
    }

    public UserModal() {

    }

    public String getStatus() {
        return status;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getWhatsappNum() {
        return whatsappNum;
    }

    public void setWhatsappNum(String whatsappNum) {
        this.whatsappNum = whatsappNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
