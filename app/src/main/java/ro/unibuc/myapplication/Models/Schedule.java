package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

@Entity(tableName = "Schedule",
indices = @Index("sid"))
public class Schedule implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    protected int sid;

    @ColumnInfo(name = "Sch Date")
    protected String date;

    @ColumnInfo(name =  "Start hour")
    protected String shift_start;

    @ColumnInfo(name = "End hour")
    protected String shift_end;

    @TypeConverters(DaoTypeConverter.class)
    protected List<Employee> employees_on_shift;

    public Schedule(String date, String shift_start, String shift_end, List<Employee> employees_on_shift) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift_start() {
        return shift_start;
    }

    public void setShift_start(String shift_start) {
        this.shift_start = shift_start;
    }

    public String getShift_end() {
        return shift_end;
    }

    public void setShift_end(String shift_end) {
        this.shift_end = shift_end;
    }

    public List<Employee> getEmployees_on_shift() {
        return employees_on_shift;
    }

    public void setEmployees_on_shift(List<Employee> employees_on_shift) {
        this.employees_on_shift = employees_on_shift;
    }
}
