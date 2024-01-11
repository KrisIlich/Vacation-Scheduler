package com.kilichapps.vacationscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kilichapps.vacationscheduler.Entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();
    @Query("SELECT * FROM excursions WHERE excursionID = :excursionId")
    Excursion getExcursionById(int excursionId);

    @Query("SELECT * FROM excursions WHERE vacationID = :vacationId")
    List<Excursion> getAllExcursionsForVacation(int vacationId);
}