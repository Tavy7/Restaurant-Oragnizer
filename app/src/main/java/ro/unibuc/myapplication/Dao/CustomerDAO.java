package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Customer;

@Dao
public interface CustomerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCustomer(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Customer... customers);

    @Update
    void updateCustomer(Customer customer);

    @Delete
    void deleteCustomer(Customer customer);

    @Delete
    void deleteAllCustomers(ArrayList<Customer> customers);

    @Query("SELECT * FROM Customer ORDER BY `User ID` ASC")
    List<Customer> getAllCustomers();
}
