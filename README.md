# Vacation Scheduler App: End-User Guide

## Getting Started with Vacation Scheduler

### Introduction
This guide is designed to assist maintenance personnel in setting up and running the "Vacation Scheduler" application. It will cover installation, configuration, routine maintenance, and troubleshooting steps.

### Installation
1. **Open with Android Studio:** Open Repository with Android Studio. 
2. **Install the App:** Deployment is available on Google Play upon request as a private tester. (email kilichpro@gmail.com)

### Initial Setup
1. **Home Screen:** Upon launching the app, you'll be greeted with the Home Screen. To begin planning your vacations, tap the "My Vacations" button.

## Planning Your Vacation

### Accessing Your Vacation List
1. **Vacation List:** After tapping "My Vacations", you'll be taken to the "Vacation List Tile View". This is where all your planned vacations are listed.
2. **Schedule a New Vacation:** To plan a new vacation, tap the purple "Schedule a Vacation" button at the top.

### Creating a New Vacation
1. **Vacation Details:** Fill in the details of your vacation including:
   - Vacation Destination: The location of your vacation.
   - Hotel Name: Where you will be staying.
   - Start Date: When your vacation begins.
   - End Date: When your vacation ends.
2. **Adding Excursions:** Tap "Add an Excursion" to plan outings and activities for your vacation. Enter the "Excursion Title" and "Excursion Date" for each one.

## Managing Vacation Details
1. **Vacation Details Menu:** Tap the three-dot icon in the upper right corner to access options such as:
   - Save Vacation: To save the vacation details you've entered.
   - Delete Vacation: To remove the vacation from your list.
   - Share Vacation: To share your vacation details with others via email or social media.
   - Set Start/End Date Reminders: To receive notifications about your vacation's upcoming start or end date.

### Editing an Existing Vacation
1. **Edit Vacation:** From the "Vacation List", tap on any existing vacation to edit details.
2. **Edit Excursions:** Scroll down to "Your Planned Excursions" to modify any excursion details.

## Sharing and Exporting

### Sending Vacation Details
1. **Exporting Vacations:** You can export a PDF report of your vacations and excursions by tapping "Export Vacations to PDF Report" from the "Vacation List Menu".
2. **Sharing PDF Report:** Once the PDF is generated, you can share it directly from the sharing interface that pops up.

## Navigation and Usability

### Navigating Back
1. **Using Back Arrows:** Each screen has a back arrow in the upper left corner. Tap it to return to the previous screen.
2. **Using Device Back Button:** You can also use your device's back button to navigate to the previous screen.

### Searching for Vacations
1. **Search Bar:** Use the search bar in the "Vacation List" to quickly find specific vacations.

## Maintenance and Support

### Updating the App
1. **App Updates:** Keep your app up-to-date through the Google Play Store to ensure the best performance and latest features.

### Troubleshooting
1. **Common Issues:** If you encounter any issues, try restarting the app or your device. For persistent problems, check for updates or reach out to support.

### Contacting Support
1. **Support Requests:** For assistance, use the app's "Help" feature or contact support through the details provided on the Google Play Store page.



## Test Plan for Unit Tests

#### Objective
- To verify that the Repository class correctly delegates the insertion of Vacation and Excursion objects to the appropriate DAOs.

#### Scope
- Testing will be limited to the insert method of the Repository class for both Vacation and Excursion entities.

#### Test Environment
- The test will be run in a controlled unit testing environment using JUnit and Mockito.

#### Test Cases
1. **Test Case for Vacation Insertion**
   - Objective: To test if Repository.insert(Vacation) correctly calls VacationDAO.insert(Vacation).
   - Input: A mock Vacation object.
   - Expected Result: The insert method of the mocked VacationDAO is called with the provided Vacation object.

2. **Test Case for Excursion Insertion**
   - Objective: To test if Repository.insert(Excursion) correctly calls ExcursionDAO.insert(Excursion).
   - Input: A mock Excursion object.
   - Expected Result: The insert method of the mocked ExcursionDAO is called with the provided Excursion object.

#### Procedure
- Initialize the testing environment with mocked DAOs and a real instance of Repository.
- Execute each test case and observe if the insert method on the respective DAO is called.

### Results of the Unit Tests
1. **Vacation Insertion Test**
   - The test was executed successfully.
   - Mockito verified that VacationDAO.insert() was called with the correct Vacation object.
   - Result: Passed.

2. **Excursion Insertion Test**
   - The test was executed successfully.
   - Mockito verified that ExcursionDAO.insert() was called with the correct Excursion object.
   - Result: Passed.

### Summaries of Changes Resulting from Completed Tests
- **Initial Implementation:** The Repository class was initially designed without considering unit testing for DAO interactions.
- **After Test Plan Execution:**
  - The Repository class was refactored to support dependency injection of DAOs, enabling easier unit testing and mocking.
  - No defects were found in the methods under test, indicating that the Repository class correctly delegates the insert operations to the DAOs.
- **Code Changes:** The constructor of the Repository class was modified to accept DAO instances for better testability.
- **Future Considerations:** Additional tests may be written to cover other CRUD operations and to handle error cases or unusual scenarios.
