package ro.unibuc.myapplication.Dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Order;

// Todo baga type converter pt liste
// https://stackoverflow.com/questions/44582397/android-room-persistent-library-typeconverter-error-of-error-cannot-figure-ou
// https://developer.android.com/training/data-storage/room/relationships
public class DaoTypeConverter {

    // For Date object
    @TypeConverter
    public Date timestampToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Time timestampToTime(Long value) {
        return value == null ? null : new Time(value);
    }

    // For time object
    @TypeConverter
    public Long timeToTimestamp(Time time) {
        if (time == null) {
            return null;
        } else {
            return time.getTime();
        }
    }

    // For item list
    @TypeConverter
    public static List<Item> stringToMenu(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        List<Item> menu = gson.fromJson(json, type);

        return menu;
    }

    @TypeConverter
    public static String menuToString(List<Item> menu) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        String json = gson.toJson(menu, type);

        return json;
    }

    // For order list
    @TypeConverter
    public static List<Order> stringToOrder(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>() {}.getType();
        List<Order> orderList = gson.fromJson(json, type);

        return orderList;
    }

    @TypeConverter
    public static String ordersToString(List<Order> orderList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Order>>() {}.getType();
        String json = gson.toJson(orderList, type);

        return json;
    }

    // For employee list
    @TypeConverter
    public static List<Employee> stringToEmpList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Employee>>() {}.getType();
        List<Employee> employeeList = gson.fromJson(json, type);

        return employeeList;
    }

    @TypeConverter
    public static String empListToString(List<Employee> orderList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(orderList, type);

        return json;
    }
}
