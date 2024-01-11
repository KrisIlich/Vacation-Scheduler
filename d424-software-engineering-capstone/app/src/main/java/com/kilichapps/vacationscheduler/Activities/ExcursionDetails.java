package com.kilichapps.vacationscheduler.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kilichapps.vacationscheduler.Database.Repository;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Plan;
import com.kilichapps.vacationscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String name;
    String date;
    int excursionID;
    int vacaID;

    EditText editName;

    TextView editStartDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    Excursion currentExcursion;
    String parentVacationStartDate;
    String parentVacationEndDate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excursion);
        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("excursionName");
        editName = findViewById(R.id.editExcursionName);
        editName.setText(name);
        date = getIntent().getStringExtra("excursionStartDate");
        editStartDate = findViewById(R.id.editExcursionDate);
        editStartDate.setText(date);
        excursionID = getIntent().getIntExtra("excursionID", -1);
        vacaID = getIntent().getIntExtra("vacationID", -1);
        parentVacationStartDate = getIntent().getStringExtra("vacationStartDate");
        parentVacationEndDate= getIntent().getStringExtra("vacationEndDate");





        String myFormat = "MM/dd/yy";
        SimpleDateFormat vsdf = new SimpleDateFormat(myFormat, Locale.US);


        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };





        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "11/24/23";
                try {
                    myCalendarStart.setTime(vsdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    private void logDetailsForPlan(Plan plan) {
        // Log details using polymorphism
        Log.d("PolymorphismExample", plan.getDetails());
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.saveExcursion) {

            if (item.getItemId() == R.id.saveExcursion) {

                try {
                    // Retrieve the vacation start and end dates from the repository
                    String parentVacationStartDateString = repository.getVacationStartDate(vacaID); // Replace with the actual method in your repository
                    String parentVacationEndDateString = repository.getVacationEndDate(vacaID); // Replace with the actual method in your repository

                    // Your existing code for parsing the excursion start date
                    String excursionStartDateString = editStartDate.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                    Date excursionStartDate = sdf.parse(excursionStartDateString);

                    // Parse the vacation start and end dates
                    Date parentVacationStartDate = sdf.parse(parentVacationStartDateString);
                    Date parentVacationEndDate = sdf.parse(parentVacationEndDateString);

                    // Check if the excursion start date is within the parent vacation range
                    if (excursionStartDate.before(parentVacationStartDate) || excursionStartDate.after(parentVacationEndDate)) {
                        Toast.makeText(ExcursionDetails.this, "Excursion start date must be within the parent vacation range", Toast.LENGTH_SHORT).show();
                        return true; // Date validation failed, don't proceed with saving
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Excursion excursion;
                if (excursionID == -1) {
                    if (repository.getmAllExcursions().size() == 0)
                        excursionID = 1;
                    else
                        excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(excursionID, editName.getText().toString(), editStartDate.getText().toString(), vacaID);
                    repository.insert(excursion);
                    logDetailsForPlan(excursion);
                } else {
                    try {
                        excursion = new Excursion(excursionID, editName.getText().toString(), editStartDate.getText().toString(), vacaID);
                        repository.update(excursion);
                        logDetailsForPlan(excursion);
                    } catch (Exception e) {
                    }
                }
                return true;
            }
        }

            if (item.getItemId() == R.id.deleteExcursion) {

            for (Excursion excursion : repository.getmAllExcursions()){
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
                repository.delete(currentExcursion);
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
            }
        if (item.getItemId() == R.id.excursionReminder) {
            String dateFromScreen = editStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", editName.getText().toString() +" has started on "+ dateFromScreen);
                Toast.makeText(ExcursionDetails.this, "Successfully Added Start Notification", Toast.LENGTH_LONG).show();
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlerts, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
