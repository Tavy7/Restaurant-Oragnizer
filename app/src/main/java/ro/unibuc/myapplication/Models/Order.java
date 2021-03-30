package ro.unibuc.myapplication.Models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Table.class,
                parentColumns = "QRCodeValue",
                childColumns = "Table id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "User ID",
                childColumns = "User ID",
                onDelete = CASCADE
        )
    },
        indices = { @Index("User ID"),
                    @Index("Table id")},
        tableName = "OrderT")

public class Order {
    @PrimaryKey
    @ColumnInfo(name = "Order id")
    protected int oid;

    @Embedded
    protected ArrayList<Item> items;

    @ColumnInfo(name = "Table id")
    protected int tableQRValue;

    @ColumnInfo(name = "Total Price")
    protected float totalPrice;

    @ColumnInfo(name = "User ID")
    protected int accountId;

    public Order(int oid, ArrayList<Item> items, int tableQRValue, float totalPrice, int accountId) {
        this.oid = oid;
        this.items = items;
        this.tableQRValue = tableQRValue;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
    }

    // Function that calculates the total price
    // of the items from array list items.
    public float findTotal(){
        float total = 0;
        for (Item item : items){
            float price = item.price;

            // If price has a discount
            if (item.discount != 0){
                // Then we update the price
                price -= item.discount * price / 100;
            }
            total += price;
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
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
}
