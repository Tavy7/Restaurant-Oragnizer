package ro.unibuc.myapplication.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ro.unibuc.myapplication.Models.Customer;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.Models.Table;
import ro.unibuc.myapplication.Models.User;

@Database(entities = {Customer.class,
                        Employee.class,
                        Item.class,
                        Order.class,
                        Schedule.class,
                        Table.class,
                        User.class}, version = 13, exportSchema = false)

public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract CustomerDAO customerDAO();
    public abstract EmployeeDAO employeeDAO();
    public abstract ItemDAO itemDao();
    public abstract OrderDAO orderDAO();
    public abstract ScheduleDAO scheduleDAO();
    public abstract TableDAO tableDAO();
    public abstract UserDAO userDAO();

    //Create instance
    private static RestaurantDatabase restaurantDatabase = null;
    protected static final String DB_NAME = "Restaurant-Database";

    public synchronized static RestaurantDatabase getInstance(Context context){
        if (restaurantDatabase == null) {
            restaurantDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    RestaurantDatabase.class, DB_NAME).
                    allowMainThreadQueries().
                    fallbackToDestructiveMigration()
                    .build();
        }
        return restaurantDatabase;
    }
}
