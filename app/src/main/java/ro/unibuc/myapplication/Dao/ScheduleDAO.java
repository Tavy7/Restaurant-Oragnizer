package ro.unibuc.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.unibuc.myapplication.Models.Schedule;

@Dao
public interface ScheduleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(Schedule schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Schedule... schedules);

    @Delete
    void deleteSchedule(Schedule schedule);

    @Delete
    void deleteAllSchedules(ArrayList<Schedule> schedules);

    // Update schedule date
    @Query("UPDATE Schedule SET `Sch date` = :date WHERE sid = :sId")
    void updateSchedule(Integer sId, Long date);

    // Update schedule start hour
    @Query("UPDATE Schedule SET `Start hour` = :start_time WHERE sid = :sId")
    void updateStartHour(Integer sId, Long start_time);

    @Query("UPDATE Schedule SET `End hour` = :end_time WHERE sid = :sId")
    void updateEndHour(Integer sId, Long end_time);

    @Query("SELECT * FROM Schedule ORDER BY sid ASC")
    List<Schedule> getAllSchedules();
}
