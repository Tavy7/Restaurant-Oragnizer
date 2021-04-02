package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Employee;

@Dao
public interface EmployeeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEmployee(Employee employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Employee... employees);

    @Update
    void updateEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

    @Delete
    void deleteAllEmployees(ArrayList<Employee> employees);

    @Query("UPDATE Employee SET `Employee role` = :role WHERE `User ID` = :eId")
    void updateEmployee(Integer eId, String role);

    @Query("SELECT * FROM Employee ORDER BY `User ID` ASC")
    List<Employee> getAllEmployees();
}
