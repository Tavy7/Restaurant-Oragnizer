package ro.unibuc.myapplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import ro.unibuc.myapplication.Dao.DaoTypeConverter;

@Entity(tableName = "TableT")
public class Table {
    @PrimaryKey
    public int QRCodeValue;
    @TypeConverters(DaoTypeConverter.class)
    List<Item> Menu;
    public Table(int QRCodeValue) {
        this.QRCodeValue = QRCodeValue;
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
}

