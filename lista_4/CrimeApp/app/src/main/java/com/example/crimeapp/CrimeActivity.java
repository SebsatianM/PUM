package com.example.crimeapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {
    private UUID crimeID;
    private Crime current;
    public ViewPager2 viewPager;
    public ViewPagerAdapter adapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crime_view_pager);

        crimeID = UUID.fromString(getIntent().getStringExtra("id"));
        current = CrimeLab.get(this).getCrime(crimeID);
        index = CrimeLab.get(this).getCrimes().indexOf(current);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index, false);
    }

    public void goToFristItem(View view){
        viewPager.setCurrentItem(0,false);
    }

    public void goToLastItem(View view){
        viewPager.setCurrentItem(CrimeLab.get(CrimeActivity.this).getCrimes().size()-1,false);
    }
}


