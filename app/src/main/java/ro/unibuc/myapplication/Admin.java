package ro.unibuc.myapplication;

import android.app.Application;

import java.util.ArrayList;

import ro.unibuc.myapplication.Dao.RestaurantDatabase;
import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.Models.Table;

public class Admin extends Application {
    protected ArrayList<Employee> employees;
    protected ArrayList<Table> tables;
    protected ArrayList<Schedule> calendar;
    protected static final String dbName = "Restaurant-Database";

    private static Admin adminInstance = null;

    public static Admin getAdminInstance(){
        if (adminInstance == null){
            adminInstance = new Admin();
        }

        return adminInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
