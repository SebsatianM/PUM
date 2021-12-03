package com.example.crimeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    private EditText renameCrimeEditText;
    private Button showCrimeDateButton;
    public CheckBox solvedCrimeCheckBox;
    private UUID crimeID;
    private Crime current;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        crimeID = UUID.fromString(getIntent().getStringExtra("id"));

        current = (Crime) CrimeLab.get(this).getCrime(crimeID);
        index = CrimeLab.get(this).getCrimes().indexOf(current);
        renameCrimeEditText = findViewById(R.id.renameCrimeEditText);
        showCrimeDateButton = findViewById(R.id.showCrimeDate);
        solvedCrimeCheckBox = findViewById(R.id.solvedCrimeCheckBox);

        renameCrimeEditText.setText(current.getTitle());
        if(current.getSolved()==true){
            solvedCrimeCheckBox.setChecked(true);
        }
        showCrimeDateButton.setText(current.getDate().toString());


        solvedCrimeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    current.setSolved(true);
                    MainActivity.crimeAdapter.notifyDataSetChanged();
                } else {
                    current.setSolved(false);
                    MainActivity.crimeAdapter.notifyDataSetChanged();
                }
            }
        });


        renameCrimeEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                current.setTitle(renameCrimeEditText.getText().toString());
                MainActivity.crimeAdapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};

            public void onTextChanged(CharSequence s, int start, int before, int count) {};
        });

    }

    public void showDate(View view) {
        final Calendar initialDate = Calendar.getInstance();

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                new TimePickerDialog(peekAvailableContext(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        current.setDate(calendar.getTime());
                        showCrimeDateButton.setText(calendar.getTime().toString());
                        current.setDate(calendar.getTime());
                        MainActivity.crimeAdapter.notifyDataSetChanged();

                    }
                },
                        initialDate.get(Calendar.HOUR_OF_DAY), initialDate.get(Calendar.MINUTE), true).show();
            }
        },
                initialDate.get(Calendar.YEAR), initialDate.get(Calendar.MONTH), initialDate.get(Calendar.DATE)).show();
    }


    public void removeCrime(View view) {
        CrimeLab.get(this).removeCrime(current);
        MainActivity.crimeAdapter.notifyDataSetChanged();
    }


}


