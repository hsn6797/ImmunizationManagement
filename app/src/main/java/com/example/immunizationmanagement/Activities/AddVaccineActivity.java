package com.example.immunizationmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.Gender;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Function;
import com.example.immunizationmanagement.Utills.ImageFactory;
import com.example.immunizationmanagement.Utills.SString;

import java.util.Calendar;

public class AddVaccineActivity extends AppCompatActivity {


    Button saveVaccine;
    EditText vaccineName;
    EditText vaccineDays;
    TextView titleText;


    DataSource ds = null;

    Vaccine vaccine = null;
    String action = "Add";
    String id = "0";


    void init(){

        titleText  = (TextView) findViewById(R.id.titleTextTV);
        saveVaccine = (Button) findViewById(R.id.babySaveB);
        vaccineName = (EditText) findViewById(R.id.babyNameET);
        vaccineDays = (EditText) findViewById(R.id.vaccineDaysET);

        ds = new DataSource(AddVaccineActivity.this);

    }

    private void saveVaccine(Vaccine vaccine){


        // Save or Edit in database
        if(!action.equals("Edit")){
            // Add Vaccine
            vaccine.addVaccine(AddVaccineActivity.this);
        }else{
            // Edit Vaccine
            vaccine.editVaccine(AddVaccineActivity.this);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine);

        init();


        // get id from previous Activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){

            action = extras.getString("Action");
            id = extras.getString("Id");

            if(action.equals("Edit")){
                // change the Title text to Edit Vaccine
                titleText.setText("Edit Vaccine");

                // Get Object from Database
                vaccine = ds.getVaccine(id);

                // assign initial values
                vaccineName.setText(vaccine.getName());
                vaccineDays.setText(String.valueOf(vaccine.getDaysAfter()));

            }
        }else{
            // change the Title text to Add Vaccine
            titleText.setText("Add Vaccine");

            vaccine = new Vaccine();
        }





        saveVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( vaccineName.getText().length() == 0 || vaccineDays.getText().length() == 0){
                    Toast.makeText(AddVaccineActivity.this,"Enter values in blank fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                vaccine.setName(SString.toTitleCase(vaccineName.getText().toString().trim()));
                vaccine.setDaysAfter(Integer.parseInt(vaccineDays.getText().toString().trim()));

                // Save in database
                saveVaccine(vaccine);
                finish();

            }
        });
    }

}
