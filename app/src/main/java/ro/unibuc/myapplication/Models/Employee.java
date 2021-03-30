package ro.unibuc.myapplication.Models;

import android.os.Parcel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Employee", inheritSuperIndices = true)
public class Employee extends User {
    @ColumnInfo(name = "Employee role")
    protected String role;

    public Employee(int uid, String name, String role) {
        super(uid, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
