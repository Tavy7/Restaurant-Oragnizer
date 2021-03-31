package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ro.unibuc.myapplication.Models.Table;

@Dao
public interface TableDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertTable(Table table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertAll(Table... tables);

    @Update
    void updateTable(Table table);

    @Query("SELECT * FROM TableT where QRCodeValue = :qrVal")
    Table getTable(int qrVal);

    @Delete
    void deleteTable(Table table);

    @Delete
    void deleteAllTables(List<Table> tables);

    @Query("SELECT * FROM TableT ORDER BY QRCodeValue ASC")
    List<Table> getAllTables();

}
