package ro.unibuc.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

@Entity(tableName = "TableT",
indices = @Index("QRCodeValue"))
public class Table implements Parcelable {
    @PrimaryKey
    public int QRCodeValue;
    @TypeConverters(DaoTypeConverter.class)
    List<Item> Menu;
    @ColumnInfo(name = "Is Occupied")
    boolean isOccupied;
    @Ignore
    boolean isSelected;
    @Ignore
    int servingEmployeeId = 0;
    @Ignore
    int orderId = 0;

    public Table(int QRCodeValue, List<Item> Menu, boolean isOccupied) {
        this.QRCodeValue = QRCodeValue;
        this.Menu = Menu;
        this.isOccupied = isOccupied;
    }

    protected Table(Parcel in) {
        QRCodeValue = in.readInt();
        Menu = in.createTypedArrayList(Item.CREATOR);
        isOccupied = in.readByte() != 0;
    }

    // Parcel
    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(QRCodeValue);
        parcel.writeTypedList(Menu);
        parcel.writeByte((byte) (isOccupied ? 1 : 0));
    }

    // Getters and setters
    public int getQRCodeValue() {
        return QRCodeValue;
    }

    public void setQRCodeValue(int QRCodeValue) {
        this.QRCodeValue = QRCodeValue;
    }

    public List<Item> getMenu() {
        return Menu;
    }

    public void setMenu(List<Item> menu) {
        Menu = menu;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String isOcupiedString(){
        if (isOccupied()){
            return "Occupied";
        }

        return "Free";
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getServingEmployeeId() {
        return servingEmployeeId;
    }

    public void setServingEmployeeId(int servingEmployeeId) {
        this.servingEmployeeId = servingEmployeeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

