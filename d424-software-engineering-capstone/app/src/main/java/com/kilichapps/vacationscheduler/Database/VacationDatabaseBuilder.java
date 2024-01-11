package com.kilichapps.vacationscheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kilichapps.vacationscheduler.DAO.ExcursionDAO;
import com.kilichapps.vacationscheduler.DAO.VacationDAO;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), VacationDatabaseBuilder.class, "VacationDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static VacationDatabaseBuilder buildDatabase(Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        VacationDatabaseBuilder.class,
                        "MyVacationDatabase.db"
                )
                .fallbackToDestructiveMigration()
                .build();
    }
}