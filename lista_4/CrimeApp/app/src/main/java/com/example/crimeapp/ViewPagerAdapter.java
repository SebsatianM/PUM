package com.example.crimeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    final Calendar calendar = Calendar.getInstance();
    private Context context;
    private List<Crime> crimes;
    public ViewPagerAdapter(Context context){
        this.crimes = CrimeLab.get(context).getCrimes();
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText renameCrimeEditText;
        private Button showCrimeDateButton;
        private CheckBox solvedCrimeCheckBox;
        private Button removeCrimeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            renameCrimeEditText = itemView.findViewById(R.id.renameCrimeEditText);
            showCrimeDateButton = itemView.findViewById(R.id.showCrimeDate);
            solvedCrimeCheckBox = itemView.findViewById(R.id.solvedCrimeCheckBox);
            removeCrimeButton = itemView.findViewById(R.id.removeCrimeButton);

        }

        public void onBind(Crime current){
            if(current.getSolved()==true){
                solvedCrimeCheckBox.setChecked(true);
            }

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

            renameCrimeEditText.setText(current.getTitle());
            renameCrimeEditText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    current.setTitle(renameCrimeEditText.getText().toString());
                    MainActivity.crimeAdapter.notifyDataSetChanged();
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                };

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                };
            });

            showCrimeDateButton.setText(current.getDate().toString());
            showCrimeDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar initialDate = Calendar.getInstance();

                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            calendar.set(year, month, day);

                            new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hour, int minute) {
                                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                                    calendar.set(Calendar.MINUTE, minute);
                                    current.setDate(calendar.getTime());
                                    showCrimeDateButton.setText(calendar.getTime().toString());
                                    current.setDate(calendar.getTime());
                                    //MainActivity.crimeAdapter.notifyDataSetChanged();

                                }
                            },
                                    initialDate.get(Calendar.HOUR_OF_DAY), initialDate.get(Calendar.MINUTE), true).show();
                        }
                    },
                            initialDate.get(Calendar.YEAR), initialDate.get(Calendar.MONTH), initialDate.get(Calendar.DATE)).show();

                }
            });

            removeCrimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrimeLab.get(context).removeCrime(current);
                    MainActivity.crimeAdapter.notifyDataSetChanged();
                }
            });
        }
        }


    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.activity_crime, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
        Crime currCrime = crimes.get(position);
        holder.onBind(currCrime);
    }

    @Override
    public int getItemCount() {
        return crimes.size();
    }

}