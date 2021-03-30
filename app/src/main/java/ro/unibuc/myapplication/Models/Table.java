package ro.unibuc.myapplication.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "TableT")
public class Table {
    @PrimaryKey
    public int QRCodeValue;
    // Todo schimbat lista din obiecte in indecsi ca sa intre in database
    @Embedded
    protected ArrayList<Item> Menu;

    public Table(int QRCodeValue, ArrayList<Item> Menu) {
        this.QRCodeValue = QRCodeValue;
        this.Menu = Menu;
    }

    // Getters and setters
    public int getQRCodeValue() {
        return QRCodeValue;
    }

    public void setQRCodeValue(int QRCodeValue) {
        this.QRCodeValue = QRCodeValue;
    }

    public ArrayList<Item> getMenu() {
        return Menu;
    }

    public void setMenu(ArrayList<Item> menu) {
        Menu = menu;
    }
}
