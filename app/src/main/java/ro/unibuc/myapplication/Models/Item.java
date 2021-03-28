package ro.unibuc.myapplication.Models;

public class Item {
    protected String name;
    protected float price;
    protected String description;
    // Discount e in procent
    protected int discount = 0;

    public Item(String name, float price, String description, int discount) {
        this.name = name;
        this.price = price;
        this.description = description;
        if (discount < 50) {
            this.discount = discount;
        }
    }
}
