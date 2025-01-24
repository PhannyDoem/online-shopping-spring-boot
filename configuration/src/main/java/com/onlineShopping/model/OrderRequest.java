package com.onlineShopping.model;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String mobileNo;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String paymentType;

    public OrderRequest(){}

    public OrderRequest(String address, String city, String email, String firstName, String lastName, String mobileNo, String paymentType, String pinCode, String state) {
        this.address = address;
        this.city = city;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.paymentType = paymentType;
        this.pinCode = pinCode;
        this.state = state;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
