package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Schedule implements Parcelable {
    private Date date;
    private Time shift_start;
    private Time shift_end;
    protected ArrayList<Employee> employees_on_shift = new ArrayList<>();

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
}
