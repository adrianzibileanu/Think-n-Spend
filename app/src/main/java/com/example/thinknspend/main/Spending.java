package com.example.thinknspend.main;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//@Entity
public class Spending implements Serializable {

    public int getUid() {
        return uid;
    }

    //  @PrimaryKey
    public int uid;

    @ColumnInfo(name = "expense_name")
    private String name;

    @ColumnInfo(name = "expense_amount")
    private float amount;

    @ColumnInfo(name = "expense_currency")
    private String currency;

    @ColumnInfo(name = "expense_type")
    private String type;

    @ColumnInfo(name = "expense_date")
    private String date;



    public Spending(int uid, float amount, String currency, String type, String name, String date){
        this.uid = uid;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.name = name;
        this.date = date;
    }

    //G&S
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
  /*  @Override
    public String toString() {
        return "Spending [amount=" + amount + ", currency=" + currency + ", type=" + type + ", name=" + name + ", date=" + date +"]";
    }*/

    @Override
    public String toString() {
        return name + " " + amount + " " + currency;
    }

}
