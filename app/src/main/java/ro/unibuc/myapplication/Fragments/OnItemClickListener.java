package ro.unibuc.myapplication.Fragments;

import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.DateNavBarModel;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.Models.Table;


// Methods are deafault because otherwise classes that implemet this interface
// will require to be abstract (case when you can not instanciate objects) or
// to override all the methods.

public interface OnItemClickListener{
    // For day navigation bar on calendar
    default void onItemClick(DateNavBarModel item){;};
    // For item CRUD
    default void onItemClick(Item item){;};
    // For table CRUD
    default void onItemClick(Table table) {;};
    // For emp CRUD
    default void onItemClick(Employee employee) {;};
    // For customer CRUD
    default void onItemClick(Customer customer) {;};
    // For schedule CRUD
    default void onItemClick(Schedule schedule) {;};
}
