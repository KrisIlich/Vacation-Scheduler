package com.kilichapps.vacationscheduler.Database;

import android.app.Application;

import com.kilichapps.vacationscheduler.DAO.ExcursionDAO;
import com.kilichapps.vacationscheduler.DAO.VacationDAO;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ExcursionDAO mExcursionDAO;
    private final VacationDAO mVacationDAO;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }
    // Modified constructor
    public Repository(ExcursionDAO excursionDAO, VacationDAO vacationDAO) {
        this.mExcursionDAO = excursionDAO;
        this.mVacationDAO = vacationDAO;
    }

    public List<Vacation> getmAllVacations() {
        databaseExecutor.execute(() -> mAllVacations = mVacationDAO.getAllVacations());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }

    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> mVacationDAO.insert(vacation));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> mVacationDAO.update(vacation));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> mVacationDAO.delete(vacation));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion> getmAllExcursions() {
        databaseExecutor.execute(() -> mAllExcursions = mExcursionDAO.getAllExcursions());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDAO.insert(excursion));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion> getmAllExcursions(int vacationId) {
        databaseExecutor.execute(() -> mAllExcursions = mExcursionDAO.getAllExcursionsForVacation(vacationId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDAO.update(excursion));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDAO.delete(excursion));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String getVacationStartDate(int vacationId) {
        final String[] startDate = {null};
        databaseExecutor.execute(() -> startDate[0] = mVacationDAO.getVacationStartDate(vacationId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return startDate[0];
    }

    public String getVacationEndDate(int vacationId) {
        final String[] endDate = {null};
        databaseExecutor.execute(() -> endDate[0] = mVacationDAO.getVacationEndDate(vacationId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return endDate[0];
    }
    public Excursion getExcursionById(int excursionId) {
        final Excursion[] excursion = {null};
        databaseExecutor.execute(() -> excursion[0] = mExcursionDAO.getExcursionById(excursionId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return excursion[0];
    }
    public Vacation getVacationById(int vacationId) {
        return mVacationDAO.getVacationById(vacationId);
    }


}