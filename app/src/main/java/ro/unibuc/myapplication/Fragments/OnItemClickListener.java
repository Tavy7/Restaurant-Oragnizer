package ro.unibuc.myapplication.Fragments;

import ro.unibuc.myapplication.Models.DateNavBarModel;
import ro.unibuc.myapplication.Models.Item;


// Methods are deafault because otherwise classes that implemet this interface
// will require to be abstract (case when you can not instanciate objects) or
// to override all the methods.

public interface OnItemClickListener{
    // For day navigation bar on calendar
    default void onItemClick(DateNavBarModel item){;};
    // For item CRUD
    default void onItemClick(Item item){;};
}
