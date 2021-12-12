package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Crime> crimeList = CrimeLab.get(this).getCrimes();
    private RecyclerView recyclerView;
    public static CrimeAdapter crimeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        crimeAdapter = new CrimeAdapter(this,crimeList);
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void add_crime(View view) {
        int position = CrimeLab.mCrimes.size();
        UUID id = UUID.randomUUID();
        CrimeLab.addCrime(id,position);

    }
}
