package com.example.zhining2_gearbook;

import java.io.Serializable;

/**
 * Functionality:
 * This class creates a gear object with given information.
 * Getter allows the outside world to get the data of this object
 * Setter allows the outside world to set the data of this object
 */
public class Gear implements Serializable {
    private String date, des, price, maker, comment;

    /**
     * Set up the attributes of the gear
     * @param date date record of this gear. must not be null
     * @param des description or the name of this gear. must not be null
     * @param price purchased price of this gear. must not be null
     * @param maker maker of this gear. must not be null
     * @param comment additional note of this gear
     */
    public Gear(String date, String des, String price, String maker, String comment) {
        this.date = date;
        this.des = des;
        this.price = price;
        this.maker = maker;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
