package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.myapplication.Models.Table;

@Dao
public abstract class TableDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertTable(Table table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAll(Table... tables);

    @Query("SELECT * FROM TableT where QRCodeValue = :qrVal")
    public abstract  Table getTable(int qrVal);

    @Delete
    abstract void deleteTable(Table table);

    @Delete
    public abstract void deleteAllTables(ArrayList<Table> tables);

    @Query("SELECT * FROM TableT ORDER BY QRCodeValue ASC")
    public abstract  List<Table> getAllTables();

}
