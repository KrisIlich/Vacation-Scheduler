package com.kilichapps.vacationscheduler.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.kilichapps.vacationscheduler.Database.Repository;

@Entity(tableName = "Excursions")
public class Excursion extends Plan {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private String excursionStartDate;
    private int VacationID;


    public Excursion(int excursionID, String excursionName, String excursionStartDate, int VacationID) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionStartDate = excursionStartDate;
        this.VacationID = VacationID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getExcursionStartDate() {
        return excursionStartDate;
    }

    public void setExcursionStartDate(String excursionStartDate) {
        this.excursionStartDate = excursionStartDate;
    }


    public int getVacationID() {
        return VacationID;
    }



    public void setVacationID(int VacationID) {
        this.VacationID = VacationID;
    }

    public String getVacationStartDate(Repository repository) {
        return repository.getVacationStartDate(VacationID);
    }
    // New method to get vacation end date
    public String getVacationEndDate(Repository repository) {
        return repository.getVacationEndDate(VacationID);
    }

    @Override
    public String getType() {
        return "Excursion";
    }

    @Override
    public String getDetails() {
        return "Excursion details: " + getExcursionName() + ", Starts on:" + getExcursionStartDate();
    }

}
