package com.example.tracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Calendar;


public class SignUp extends AppCompatActivity
{
    private String[] arraySpinnerDay = new String[31];
    private String[] arraySpinnerYear = new String[100];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        int counter = 0;
        for(int x = 0; x<31; x++)
        {
            counter += 1;
            this.arraySpinnerDay[x] = "" + counter;
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int end = year-100;
        int index = 0;
        for(int x=year;x>end;x--)
        {
            this.arraySpinnerYear[index] = "" + x;
            index++;
        }


        Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDOBDay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerDay);
        spinnerDay.setAdapter(adapter);

        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerDOBYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerYear);
        spinnerYear.setAdapter(adapterYear);
    }



}
