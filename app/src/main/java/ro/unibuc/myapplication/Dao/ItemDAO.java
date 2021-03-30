package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Item;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item... items);

    @Delete
    void deleteItem(Item item);

    @Delete
    void deleteAllItems(ArrayList<Item> items);

    @Query("UPDATE Item SET discount = :sDiscount WHERE iid = :sId")
    void updateItem(Integer sId, Integer sDiscount);

    @Query("SELECT * FROM Item ORDER BY iid ASC")
    List<Item> getAllItems();
}
