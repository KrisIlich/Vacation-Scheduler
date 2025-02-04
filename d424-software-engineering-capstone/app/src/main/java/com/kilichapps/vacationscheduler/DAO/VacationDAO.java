package com.kilichapps.vacationscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kilichapps.vacationscheduler.Entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT vacationStartDate FROM VACATIONS WHERE vacationID = :vacationId")
    String getVacationStartDate(int vacationId);

    @Query("SELECT vacationEndDate FROM VACATIONS WHERE vacationID = :vacationId")
    String getVacationEndDate(int vacationId);

    @Query("SELECT * FROM Vacations WHERE vacationID = :vacationId")
    Vacation getVacationById(int vacationId);
}