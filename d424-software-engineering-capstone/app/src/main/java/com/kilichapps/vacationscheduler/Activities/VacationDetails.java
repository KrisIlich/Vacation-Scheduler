package com.kilichapps.vacationscheduler.Activities;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kilichapps.vacationscheduler.Database.Repository;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Plan;
import com.kilichapps.vacationscheduler.Entities.Vacation;
import com.kilichapps.vacationscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String vacationName;
    int vacationID;
    String hotelName;
    String vacationStartDate;
    String vacationEndDate;
    EditText editName;
    EditText editHotel;
    TextView editDate;

    TextView editStartDate;
    TextView editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    DatePickerDialog.OnDateSetListener dialogStartDate;


    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    List<Excursion> excursion = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(getApplication());
        setContentView(R.layout.activity_add_vacation);
        Button button = findViewById(R.id.button);

        editName = findViewById(R.id.editVacationText);
        vacationName = getIntent().getStringExtra("vacationName");
        editName.setText(vacationName);

        editHotel = findViewById(R.id.editHotelText);
        hotelName = getIntent().getStringExtra("hotelName");
        editHotel.setText(hotelName);

        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        editStartDate = findViewById(R.id.editStartDate);
        editStartDate.setText(vacationStartDate);

        vacationEndDate = getIntent().getStringExtra("vacationEndDate");
        editEndDate = findViewById(R.id.editEndDate);
        editEndDate.setText(vacationEndDate);



        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        vacationID = getIntent().getIntExtra("vacationID", -1);


        Log.d("VacationDetails", "Vacation Start Date: " + editStartDate.getText().toString());
        Log.d("VacationDetails", "Vacation End Date: " + editEndDate.getText().toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationStartDate", editStartDate.getText().toString());
                intent.putExtra("vacationEndDate", editEndDate.getText().toString());
                intent.putExtra("vacationID", vacationID);
                Log.d("VacationDetails", "Vacation Start Date after clicking excursions: " + editStartDate.getText().toString());
                Log.d("VacationDetails", "Vacation End Date after clicking excursions: " + editEndDate.getText().toString());
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
            excursionAdapter.setExcursions(filteredExcursions);
        }

        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        dialogStartDate = new DatePickerDialog.OnDateSetListener() {
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
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, dialogStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "11/25/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabelEnd() {
        Log.d("VacationDetails", "updateLabelEnd() called");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }


    private void updateLabelStart() {
        Log.d("VacationDetails", "UpdateLabelStart() called");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    private void logDetailsForPlan(Plan plan) {
        // Log details using polymorphism
        Log.d("PolymorphismExample", plan.getDetails());
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Excursion> allExcursions=repository.getmAllExcursions();
        RecyclerView recyclerView=findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter=new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
            excursionAdapter.setExcursions(filteredExcursions);
        }
        excursionAdapter.setExcursions(filteredExcursions);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveVacation) {
            try {
                String startDateString = editStartDate.getText().toString();
                String endDateString = editEndDate.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);

                if (startDate.after(endDate)) {
                    Toast.makeText(VacationDetails.this, "Start date must be before end date", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }catch (Exception e) {
            }

                Vacation vacation;
                if (vacationID == -1) {
                    Toast.makeText(VacationDetails.this, "Vacation has saved", Toast.LENGTH_SHORT).show();
                    if (repository.getmAllVacations().size() == 0) vacationID = 1;
                    else
                        vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.insert(vacation);
                    logDetailsForPlan(vacation);
                } else {
                    try {
                        vacation = new Vacation(vacationID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.update(vacation);
                        logDetailsForPlan(vacation);
                    } catch (Exception e) {
                    }
                    Toast.makeText(VacationDetails.this, "Vacation has saved", Toast.LENGTH_SHORT).show();
                }return true;

            }


        if (item.getItemId() == R.id.deleteVacation) {
            for (Vacation vaca : repository.getmAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VacationDetails.this, "You must delete any associated excursions before being able to delete this Vacation!", Toast.LENGTH_LONG).show();
            }
            return true;
        }


        if (item.getItemId() == R.id.share) {
            StringBuilder shareText = new StringBuilder();
            shareText.append("Vacation Name: ").append(editName.getText().toString()).append("\n");
            shareText.append("Hotel Name: ").append(editHotel.getText().toString()).append("\n");
            shareText.append("Start Date: ").append(editStartDate.getText().toString()).append("\n");
            shareText.append("End Date: ").append(editEndDate.getText().toString()).append("\n\n");
            shareText.append("Excursions:\n");
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) {
                    shareText.append("- ").append(excursion.getExcursionName()).append("\n");
                    // Add more details if needed
                    shareText.append("  Name: ").append(excursion.getExcursionName()).append("\n");
                    shareText.append("  Date: ").append(excursion.getExcursionStartDate()).append("\n");
                    // Add more details as needed
                }
            }

            // Create a sharing intent
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Vacation Details");
            sendIntent.setType("text/plain");

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vacation Details");
            emailIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());


            // Show a chooser dialog
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

            return true;
        }
        if (item.getItemId() == R.id.notify_start){
            String dateFromScreen = editStartDate.getText().toString();
            String endDate = editEndDate.getText().toString() ;
            String myFormat ="MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
            Date myDate = null;
            try{
                myDate=sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetails.this,MyReceiver.class);
                intent.putExtra("key",editName.getText().toString()  +" has started on "+ dateFromScreen + " and will end" + endDate);
                Toast.makeText(VacationDetails.this, "Successfully Added Start Notification", Toast.LENGTH_LONG).show();
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this,++MainActivity.numAlerts,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);}
            catch(Exception e){
                e.printStackTrace();
            }
            return true;
        }
        if (item.getItemId() == R.id.notify_end){
            String dateFromScreen =editEndDate.getText().toString();
            String startDate = editEndDate.getText().toString();

            String myFormat ="MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
            Date myDate = null;
            try{
                myDate=sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetails.this,MyReceiver.class);
                intent.putExtra("key",editName.getText().toString() +" that started "+ startDate+ " has ended on "+ dateFromScreen );
                Toast.makeText(VacationDetails.this, "Successfully Added End Notification", Toast.LENGTH_LONG).show();
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this,++MainActivity.numAlerts,intent,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);}
            catch(Exception e){
                e.printStackTrace();
            }
            return true;

        }



        return super.onOptionsItemSelected(item);

    }
}



