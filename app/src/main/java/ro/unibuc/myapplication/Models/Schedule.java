package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

@Entity(tableName = "Schedule")
public class Schedule implements Parcelable {
    @PrimaryKey
    protected int sid;

    @ColumnInfo(name = "Sch date")
    @TypeConverters(DaoTypeConverter.class)
    protected Date date;

    @ColumnInfo(name =  "Start hour")
    @TypeConverters(DaoTypeConverter.class)
    protected Time shift_start;

    @ColumnInfo(name = "End hour")
    @TypeConverters(DaoTypeConverter.class)
    protected Time shift_end;

    @Embedded
    protected ArrayList<Employee> employees_on_shift = new ArrayList<>();

    public Schedule(int sid, Date date, Time shift_start, Time shift_end, ArrayList<Employee> employees_on_shift) {
        this.sid = sid;
        this.date = date;
        this.shift_start = shift_start;
        this.shift_end = shift_end;
        this.employees_on_shift = employees_on_shift;
    }

    protected Schedule(Parcel in) {
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    //Getters and setters

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getShift_start() {
        return shift_start;
    }

    public void setShift_start(Time shift_start) {
        this.shift_start = shift_start;
    }

    public Time getShift_end() {
        return shift_end;
    }

    public void setShift_end(Time shift_end) {
        this.shift_end = shift_end;
    }

    public ArrayList<Employee> getEmployees_on_shift() {
        return employees_on_shift;
    }

    public void setEmployees_on_shift(ArrayList<Employee> employees_on_shift) {
        this.employees_on_shift = employees_on_shift;
    }
}
