package ro.unibuc.myapplication.Models;

import android.os.Parcel;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "Customer", inheritSuperIndices = true)
public class Customer extends User{
    @ColumnInfo(name = "Credit Card Number")
    protected String creditCard;
    @Embedded
    protected ArrayList<Order> orders;

    public Customer(int uid, String name, String creditCard, ArrayList<Order> orders) {
        super(uid, name);
        this.creditCard = creditCard;
        this.orders = orders;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
