package ro.unibuc.myapplication.Models;

import java.util.ArrayList;

public class Order {
    protected int id;
    protected ArrayList<Item> items;
    protected int tableQRValue;
    protected float totalPrice;
    protected int accountId;

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
}
