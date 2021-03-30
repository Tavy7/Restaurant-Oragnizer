package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Order;

@Dao
public interface OrderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Order order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Order... orders);

    @Delete
    void deleteOrder(Order order);

    @Delete
    void deleteAllOrders(ArrayList<Order> orders);

    @Query("SELECT * FROM OrderT ORDER BY `Order id` ASC")
    List<Order> getAllOrders();
}
