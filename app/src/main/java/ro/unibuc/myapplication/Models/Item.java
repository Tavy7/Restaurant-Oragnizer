package ro.unibuc.myapplication.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Item")
public class Item {
    @PrimaryKey
    protected int iid;
    @ColumnInfo(name = "Item name")
    protected String name;
    @ColumnInfo(name = "Price")
    protected float price;
    @ColumnInfo(name = "Item Info")
    protected String description;

    //todo bitmap picture;

    // Discount e in procent
    @ColumnInfo(name = "Discount")
    protected int discount = 0;

    public Item(int iid, String name, float price, String description, int discount) {
        this.iid = iid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = 0;
        if (discount < 50) {
            this.discount = discount;
        }
    }

    // Getters and setters
    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
