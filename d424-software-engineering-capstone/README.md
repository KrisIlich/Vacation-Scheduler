•  title and purpose of the application

    Title: VacationScheduler
    Purpose: To have one area to schedule and keep track of your vacations. 

•  directions for how to operate the application and reach all the rubric aspects

        User should hit the my vacations button to enter their database user interface. 
        They then can add select or update the vacations that vacation list holds. 
        Users can also add a sample vacation list to the list. They can enter the vacation details view of a new vacation or an existing one. 
        From here a user can modify the vacation object at question.  
        Users can also set reminders for vacation start date and end date or share the vacation information via sms, clipboard, email, etc.
        From there a user can edit excursions specifically within the parent vacation.
        Users can set name start date and reminder for excursions. 
        Vacations can be saved or deleted, and there is backwards arrow navigation persistent through the application.


        1. Enter the database
            a. Click enter button to navigate to the vacation list

        2. Add/Select/Update Vacation List:
            a. Create new vacations
            b. Select from pre-existing Vacation
            c. Add sample vacations

        3. Add Vacation Details and Features for each Vacation:
            a. Display a detailed view for adding, updating, and viewing vacation information.
            b. CRUD operations (Create, Read, Update, Delete) for vacation information.
            c. Validation for correct date formatting.
            d. Validation that the end date is after the start date.
            e. User-settable alerts for start and end dates with title and status.
            f. Sharing feature for vacation details via email, clipboard, or SMS.
            g. Display a list of excursions associated with each vacation.
            h. CRUD operations for excursions.


        4. Add Excursion Details and features for each Excursion:
            a. Display a detailed view for adding, updating, and viewing excursion information.
            b. CRUD operations for excursion information.
            c. Validation for correct date formatting.
            d. User-settable alerts for excursion date with title.
    
        5. GUI Elements that make this possible
            Home screen with enter button - loads repository
            List of vacations - recycler view that can store many vacatoins
            List of excursions associated with a vacation - recyclerview that can store many excursions nested within vacations
            Detailed vacation view - stylized edit fields for each variable in vacation along with navigation
            Detailed excursion view - stylized edit fields for each variable in excursion along with navigation
            Back arrow navigation to go to parent
            Menu option items for class functionality
            Button navigation

        6. Version control
            push on each part.


•  to which android version the signed APK is deployed

    min sdk 26
    max sdk 34

•  a link to the git repository

    https://gitlab.com/wgu-gitlab-environment/student-repos/kilich/d308-mobile-application-development-android/-/tree/working_branch?ref_type=heads