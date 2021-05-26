package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Item",
indices = {@Index("iid")})
public class Item implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    protected Integer iid;
    @ColumnInfo(name = "Item name")
    protected String name;
    @ColumnInfo(name = "Price")
    protected float price;
    @ColumnInfo(name = "Item Info")
    protected String description;
    // Discount e in procent
    @ColumnInfo(name = "Discount")
    protected int discount = 0;
    //todo bitmap picture;
    @Ignore
    boolean isSelected;
    @Ignore
    int quantity = 1;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String name, float price, String description, int discount) {
        //this.iid = iid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = 0;
        if (discount < 50) {
            this.discount = discount;
        }
    }

    protected Item(Parcel in) {
        iid = in.readInt();
        name = in.readString();
        price = in.readFloat();
        description = in.readString();
        discount = in.readInt();
    }

    // Parcel
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iid);
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeString(description);
        parcel.writeInt(discount);
    }

    // Getters and setters
    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
