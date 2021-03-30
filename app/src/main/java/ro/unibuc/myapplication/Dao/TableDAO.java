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
public interface TableDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTable(Table table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Table... tables);

    @Delete
    void deleteTable(Table table);

    @Delete
    void deleteAllTables(ArrayList<Table> tables);

    @Query("SELECT * FROM TableT ORDER BY QRCodeValue ASC")
    List<Table> getAllTables();
}
