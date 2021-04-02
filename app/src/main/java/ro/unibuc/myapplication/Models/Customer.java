package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.List;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

@Entity(tableName = "Customer", inheritSuperIndices = true)
public class Customer extends User implements Parcelable {
    @ColumnInfo(name = "Credit Card Number")
    protected String creditCard;
    @TypeConverters(DaoTypeConverter.class)
    protected List<Order> orders;
    @ColumnInfo(name = "Email")
    protected String email;

    public Customer(String name, String creditCard, List<Order> orders, String email) {
        super(name);
        this.creditCard = creditCard;
        this.orders = orders;
        this.email = email;
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    protected Customer(Parcel in) {
        super(in);
        creditCard = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(creditCard);
    }


    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
