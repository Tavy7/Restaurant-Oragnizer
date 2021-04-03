package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

import static androidx.room.ForeignKey.CASCADE;

//Todo change foreign keys with dao type converter 
@Entity(foreignKeys = {
        @ForeignKey(
                entity = Table.class,
                parentColumns = "QRCodeValue",
                childColumns = "Table id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Employee.class,
                parentColumns = "User ID",
                childColumns = "User ID",
                onDelete = CASCADE
        )
    },
        indices = { @Index ("Order id"),
                    @Index("User ID"),
                    @Index("Table id")},
        tableName = "OrderT")

public class Order implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Order id")
    protected int oid;

    @TypeConverters(DaoTypeConverter.class)
    protected List<Item> items;

    @ColumnInfo(name = "Table id")
    protected int tableQRValue;

    @ColumnInfo(name = "Total Price")
    protected float totalPrice;

    @ColumnInfo(name = "User ID")
    protected int accountId;

    @ColumnInfo(name = "Order date")
    protected String orderDate;

    public Order(List<Item> items, int tableQRValue, float totalPrice, int accountId, String orderDate) {
        this.items = items;
        this.tableQRValue = tableQRValue;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
        this.orderDate = orderDate;
    }

    protected Order(Parcel in) {
        oid = in.readInt();
        items = in.createTypedArrayList(Item.CREATOR);
        tableQRValue = in.readInt();
        totalPrice = in.readFloat();
        accountId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oid);
        dest.writeTypedList(items);
        dest.writeInt(tableQRValue);
        dest.writeFloat(totalPrice);
        dest.writeInt(accountId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    // Getters and setters

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTableQRValue() {
        return tableQRValue;
    }

    public void setTableQRValue(int tableQRValue) {
        this.tableQRValue = tableQRValue;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}