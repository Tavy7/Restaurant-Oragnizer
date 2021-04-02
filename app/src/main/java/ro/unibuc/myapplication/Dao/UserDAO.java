package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Customer;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCustomer(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Customer... customers);

    @Delete
    void deleteCustomer(Customer customer);

    @Delete
    void deleteAllCustomers(ArrayList<Customer> customers);

    @Query("UPDATE Customer SET `Credit Card Number` = :creditCard WHERE `User ID` = :cId")
    void updateCustomer(Integer cId, String creditCard);

    @Query("SELECT * FROM Customer ORDER BY `User ID` ASC")
    List<Customer> getAllCustomers();
}
