package ro.unibuc.myapplication.Dao;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Time;
import java.util.Date;
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
}
