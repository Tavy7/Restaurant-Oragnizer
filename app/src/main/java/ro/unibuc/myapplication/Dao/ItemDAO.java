package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ro.unibuc.myapplication.Models.Item;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertItem(Item item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Item... items);

    @Update
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Delete
    void deleteAllItems(List<Item> items);

    @Query("UPDATE Item SET discount = :sDiscount WHERE iid = :sId")
    void updateItemDiscount(Integer sId, Integer sDiscount);

    @Query("SELECT * FROM Item ORDER BY iid ASC")
    List<Item> getAllItems();
}
