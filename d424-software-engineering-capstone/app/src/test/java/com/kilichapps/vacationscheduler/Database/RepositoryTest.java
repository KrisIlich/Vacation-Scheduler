package com.kilichapps.vacationscheduler.Database;

import com.kilichapps.vacationscheduler.DAO.ExcursionDAO;
import com.kilichapps.vacationscheduler.DAO.VacationDAO;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Vacation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RepositoryTest {

    private Repository repository;
    private VacationDAO mockVacationDAO;
    private ExcursionDAO mockExcursionDAO;

    @Before
    public void setUp() {
        // Mock the DAOs
        mockVacationDAO = Mockito.mock(VacationDAO.class);
        mockExcursionDAO = Mockito.mock(ExcursionDAO.class);

        // Initialize the repository with the mocked DAOs
        repository = new Repository(mockExcursionDAO, mockVacationDAO);
    }

    @Test
    public void testAddExcursion() {
        // Create a sample excursion
        Excursion sampleExcursion = new Excursion(1, "Test Excursion", "01/03/2023", 1);
        // Perform the add operation
        repository.insert(sampleExcursion);
        // Verify the excursion was added
        Mockito.verify(mockExcursionDAO).insert(sampleExcursion);
    }
    @Test
    public void testAddVacation() {
        // Create a sample vacation
        Vacation sampleVacation = new Vacation(1, "Test Vacation", "Test Hotel", "01/01/2023", "01/07/2023");
        // Perform the add operation
        repository.insert(sampleVacation);
        // Verify the vacation was added
        Mockito.verify(mockVacationDAO).insert(sampleVacation);
    }
}