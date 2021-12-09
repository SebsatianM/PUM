package com.example.crimeapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CrimeLab {
    private static CrimeLab sCrimeLab;
    public static List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {

        mCrimes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setDate(new Date());
            crime.setUUID(UUID.randomUUID());
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }



    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }


    public void removeCrime(Crime current) {
        mCrimes.remove(current);
    }

    public static void addCrime(UUID id,int pos){
        Crime crime = new Crime();
        crime.setTitle("Crime #" + pos);
        crime.setDate(new Date());
        crime.setUUID(id);
        crime.setSolved(false);
        mCrimes.add(crime);
        MainActivity.crimeAdapter.notifyDataSetChanged();
    }

}

