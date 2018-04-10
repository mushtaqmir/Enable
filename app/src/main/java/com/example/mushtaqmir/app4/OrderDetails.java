package com.example.mushtaqmir.app4;


import java.io.Serializable;
import java.util.Date;

public class OrderDetails implements Serializable{


    String fuelType;
    String category;
    double fuelQty;
    double fuelAmount;
    boolean fullTank;
    int fuelQtyAmtIndentifier;
    String ModeOfPayment;
    String orderDateTime;



    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getFuelQty() {
        return fuelQty;
    }

    public void setFuelQty(double fuelQty) {
        this.fuelQty = fuelQty;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public boolean isFullTank() {
        return fullTank;
    }

    public void setFullTank(boolean fullTank) {
        this.fullTank = fullTank;
    }

    public int getFuelQtyAmtIndentifier() {
        return fuelQtyAmtIndentifier;
    }

    public void setFuelQtyAmtIndentifier(int fuelQtyAmtIndentifier) {
        this.fuelQtyAmtIndentifier = fuelQtyAmtIndentifier;
    }
    public String getModeOfPayment() {
        return ModeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.ModeOfPayment = modeOfPayment;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

}
