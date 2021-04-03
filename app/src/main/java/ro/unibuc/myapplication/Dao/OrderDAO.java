package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Order;

@Dao
public interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrder(Order order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Order... orders);

    // Update
    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Delete
    void deleteAllOrders(ArrayList<Order> orders);

    @Query("SELECT * FROM OrderT ORDER BY `Order id` ASC")
    List<Order> getAllOrders();
}
