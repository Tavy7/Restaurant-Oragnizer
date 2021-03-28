package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleModel implements Parcelable {
    private Date date;
    private Time shift_start;
    private Time shift_end;
    protected ArrayList<Employee> employees_on_shift = new ArrayList<>();

    protected ScheduleModel(Parcel in) {
    }

    public static final Creator<ScheduleModel> CREATOR = new Creator<ScheduleModel>() {
        @Override
        public ScheduleModel createFromParcel(Parcel in) {
            return new ScheduleModel(in);
        }

        @Override
        public ScheduleModel[] newArray(int size) {
            return new ScheduleModel[size];
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
