package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
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
    protected float totalPrice = 0;

    @ColumnInfo(name = "User ID")
    protected int accountId;

    @ColumnInfo(name = "Order date")
    protected String orderDate;

    @ColumnInfo(name = "Order finished")
    protected boolean orderFinished = false;

    public Order(List<Item> items, int tableQRValue, int accountId, String orderDate, boolean orderFinished) {
        this.items = items;
        if (items.size() > 0){
            this.totalPrice = findTotal(items);
        }

        this.tableQRValue = tableQRValue;
        this.accountId = accountId;
        this.orderDate = orderDate;
        this.orderFinished = orderFinished;
    }

    @Ignore
    public Order(int tableQRValue, int accountId){
        this.tableQRValue = tableQRValue;
        this.accountId = accountId;
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

    // Function that calculates the total price
    // of the items from array list items.
    public float findTotal(List<Item> itemList){
        float total = 0;
        for (Item item : itemList){
            float price = item.getPrice();

            int discount = item.getDiscount();
            // If price has a discount
            if (discount != 0){
                // Then we update the price
                price -= discount * price / 100;
            }
            total += (price * item.getQuantity());
        }

        return total;
    }


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

    public void appendItem(Item item) { this.items.add(item); }

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

    public boolean isOrderFinished() {
        return orderFinished;
    }

    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }
}