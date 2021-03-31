package ro.unibuc.myapplication.Dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import ro.unibuc.myapplication.Models.Item;
import ro.unibuc.myapplication.Models.Table;

// Todo baga type converter pt liste
// https://stackoverflow.com/questions/44582397/android-room-persistent-library-typeconverter-error-of-error-cannot-figure-ou
// https://developer.android.com/training/data-storage/room/relationships
public class DaoTypeConverter {
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

    @TypeConverter
    public Long timeToTimestamp(Time time) {
        if (time == null) {
            return null;
        } else {
            return time.getTime();
        }
    }

    @TypeConverter
    public static List<Item> stringToMenu(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Table>>() {}.getType();
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
}
