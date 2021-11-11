package com.example.calculatorapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private float count;
    private String pom = "";
    private String context = "";
    private int number_1;
    private int number_2;
    private float res;
    private TextView result;
    private String pressedDigit;
    private String pressedAction = "none";
    private String err = "Dziel/0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id._result);
        result.setText("");

        if (savedInstanceState != null) {
            context = savedInstanceState.getString("context");

        }
        if (result != null)
            result.setText(context);
    }

    @SuppressLint("SetTextI18n")
    public void readDigit(View v) {
        pressedDigit = getResources().getResourceEntryName(v.getId()).substring(1);
        pom = pom + pressedDigit;
        context = context + pressedDigit;

        if (result != null)
            result.setText(context);
    }

    @SuppressLint("SetTextI18n")
    public void readAction(View v) {

        if (pressedAction=="none") {
            number_1 = Integer.parseInt(pom);
            switch (v.getId()) {
                case (R.id._add):
                    pressedAction = "+";
                    break;
                case (R.id._diff):
                    pressedAction = "-";
                    break;
                case (R.id._multipy):
                    pressedAction = "*";
                    break;
                case (R.id._divide):
                    pressedAction = "/";
                    break;
            }
            context = pom+pressedAction;
            pom = "";
        }

        if (result != null)
            result.setText(context);
    }

    public void readClear(View view) {
        pom = "";
        context = "";
        number_1 = 0;
        number_2 = 0;
        pressedAction = "none";
        res = 0;
        if (result != null)
            result.setText(context);
    }

    public void readEqual(View view) {
        number_2 = Integer.parseInt(pom);
        switch (pressedAction) {
            case ("+"):
                res = number_1 + number_2;
                break;
            case ("-"):
                res = number_1 - number_2;
                break;
            case ("/"):
                if (number_2 == 0)
                {
                    if (result != null)
                        context = err;
                        result.setText(context);
                }
                else
                    {
                        res = number_1 / number_2;
                    }
                break;
            case ("*"):
                res = number_1 * number_2;
                break;
        }
        if (result != null && number_2!= 0)
            context = Float.toString(res);
            result.setText(context);

        number_1 = 0;
        pressedAction = "none";
        pom = "";
        number_2 = 0;



    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("context", context);

    }
}