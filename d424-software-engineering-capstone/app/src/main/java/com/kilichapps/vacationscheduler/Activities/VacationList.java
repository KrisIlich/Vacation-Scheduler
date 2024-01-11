package com.kilichapps.vacationscheduler.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kilichapps.vacationscheduler.Database.Repository;
import com.kilichapps.vacationscheduler.Entities.Excursion;
import com.kilichapps.vacationscheduler.Entities.Vacation;
import com.kilichapps.vacationscheduler.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VacationList extends AppCompatActivity {

    private Repository repository;
    private SearchView searchView;
    private RecyclerView vacationRecyclerView;
    private VacationAdapter vacationAdapter;
    private List<Vacation> allVacations;
    int vacationID;
    private int selectedVacationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vacation);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.vacationRecyclerView);
        repository=new Repository(getApplication());
        allVacations = repository.getmAllVacations();

        // Add this log statement to check the initial data
        Log.d("VacationList", "Initial data: " + allVacations.toString());


        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search Vacations");
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearch(newText);
                return false;
            }
        });

        //System.out.println(getIntent().getStringExtra("test"));

    }

    private void handleSearch(String text) {

        Log.d("VacationList", "handleSearch: " + text);
        List<Vacation> filteredVacations = new ArrayList<>();
        filteredVacations.clear(); // Clear the previous filtered results
        for (Vacation v : allVacations) {
            if (v.getVacationName().toLowerCase().contains(text.toLowerCase())) {
                filteredVacations.add(v);
            }
        }
        Log.d("VacationList", "Filtered data: " + filteredVacations.toString());
        vacationAdapter.setVacations(filteredVacations);
        if (filteredVacations.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    private void exportToPDF() {
        String fileName = "VacationReport.pdf";
        File pdfFile = new File(getExternalFilesDir(null), fileName);
        if (!pdfFile.getParentFile().exists()) {
            pdfFile.getParentFile().mkdirs();
        }

        List<Vacation> allVacations = repository.getmAllVacations();
        if (allVacations != null && !allVacations.isEmpty()) {
            fileName = "VacationReport.pdf";
            pdfFile = new File(getExternalFilesDir(null), fileName);

            try {
                PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile.getAbsolutePath()));
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                for (Vacation currentVacation : allVacations) {
                    List<Excursion> excursionList = repository.getmAllExcursions(currentVacation.getVacationID());

                    // Add content to the PDF for each vacation
                    document.add(new Paragraph("Vacation Report"));
                    document.add(new Paragraph("Vacation Name: " + currentVacation.getVacationName()));
                    document.add(new Paragraph("Hotel Name: " + currentVacation.getHotelName()));
                    document.add(new Paragraph("Start Date: " + currentVacation.getVacationStartDate()));
                    document.add(new Paragraph("End Date: " + currentVacation.getVacationEndDate()));

                    // Add Excursion details
                    document.add(new Paragraph("\nExcursions:"));
                    if (excursionList != null && !excursionList.isEmpty()) {
                        for (Excursion excursion : excursionList) {
                            document.add(new Paragraph("- Excursion Name: " + excursion.getExcursionName()));
                            document.add(new Paragraph("  Date: " + excursion.getExcursionStartDate()));
                            // Add more details as needed
                        }
                    } else {
                        document.add(new Paragraph("No excursions for this vacation."));
                    }

                    // Add a separator between vacations
                    document.add(new Paragraph("\n------------------------------\n"));
                }

                // Save the document
                document.close();

                // Create an Intent to send the PDF
                Uri pdfFileUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", pdfFile);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM, pdfFileUri);

                // Add a chooser to let the user choose an app for sharing
                Intent chooser = Intent.createChooser(shareIntent, "Share PDF");
                if (chooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                } else {
                    Toast.makeText(this, "No app to handle PDF sharing", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                Log.e("VacationList", "Error creating PDF: " + e.getMessage());
                Toast.makeText(this, "Failed to export PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a toast if there are no vacations to export
            Toast.makeText(this, "No Vacations to Export", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        searchView.setQueryHint("Search Vacations");
        RecyclerView recyclerView=findViewById(R.id.vacationRecyclerView);
        vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Vacation> allVacations = repository.getmAllVacations();
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sample) {
            Repository repo = new Repository(getApplication());
            Vacation vacation = new Vacation(1, "Maui", "Mickey Mouse Maui Resort", "12/23/2023", "12/28/2023");
            repo.insert(vacation);
            vacation = new Vacation(2, "Cuba", "Guantanemo Bay", "10/23/2023", "12/28/2023");
            repo.insert(vacation);
            Excursion excursion=new Excursion(1, "Volcano Swim Tour","12/25/2023", 1);
            repo.insert(excursion);
            excursion=new Excursion(2, "Surf with Sharks", "12/26/2023", 1);
            repo.insert(excursion);
            Toast.makeText(VacationList.this, "Sample data added if none exists", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.exportToPDF) {
            exportToPDF();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}