package com.example.tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SignUp extends AppCompatActivity {
    private String[] arraySpinnerDay = new String[31];
    private String[] arraySpinnerYear = new String[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        int counter = 0;
        for (int x = 0; x < 31; x++) {
            counter += 1;
            this.arraySpinnerDay[x] = "" + counter;
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int end = year - 100;
        int index = 0;
        for (int x = year; x > end; x--) {
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


        TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        textViewErrorMessage.setVisibility(View.GONE);

        EditText editTextHeightInches = (EditText) findViewById(R.id.editTextHeightInches);
        editTextHeightInches.setVisibility(View.GONE);

        //Measurement Spinner
        Spinner spinnerMeasurement = (Spinner) findViewById(R.id.spinnerMeasurement);
        spinnerMeasurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                measurementChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // measurementChanged();
            }
        });


        Button ButtonSignUp = (Button) findViewById(R.id.buttonSignUp);
        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpSubmit();
            }
        });


    } //End of OnCreate

    public void measurementChanged() {
        Spinner spinnerMesurment = (Spinner) findViewById(R.id.spinnerMeasurement);
        String stringMeasurement = spinnerMesurment.getSelectedItem().toString();

        EditText editTextHeightCm = (EditText) findViewById(R.id.editTextHeightCm);
        EditText editTextHeightInches = (EditText) findViewById(R.id.editTextHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;

        TextView textViewCm = (TextView) findViewById(R.id.textViewCm);
        TextView textViewKg = (TextView) findViewById(R.id.textViewKg);

        if (stringMeasurement.startsWith("I")) {
            // Imperial
            editTextHeightInches.setVisibility(View.VISIBLE);
            textViewCm.setText("feet and inches");
            textViewKg.setText("pound");

            //CM to FEET
            try {
                heightCm = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException nfe) {

            }
            if (heightCm != 0) {
                heightFeet = (heightCm * .3937008) / 12;
                heightFeet = (int) heightFeet;
                editTextHeightCm.setText("" + heightFeet);
            }


        } else {
            // Metric
            editTextHeightInches.setVisibility(View.GONE);
            textViewCm.setText("cm");
            textViewKg.setText("kg");

            //Feet & Inches to CM
            //Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException e) {

            }
            //Convert Inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            } catch (NumberFormatException e) {

            }
            if (heightFeet != 0 && heightInches != 0) {
                heightCm = ((heightFeet * 12) + heightInches) * 2.54;
                heightCm = (int) heightCm;
                editTextHeightCm.setText("" + heightCm);
            }
        }
        //Weight
        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        String stringWeight = editTextWeight.getText().toString();
        double doubleWeight = 0;

        try {
            doubleWeight = Double.parseDouble(stringWeight);
        } catch (NumberFormatException nfe) {
        }

        if (doubleWeight != 0) {

            if (stringMeasurement.startsWith("I")) {
                //kg to pounds
                doubleWeight = Math.round(doubleWeight / .45359237);

            } else {
                //Pounds to KG
                doubleWeight = Math.round(doubleWeight * 0.45359237);
            }
            editTextWeight.setText("" + doubleWeight);
        }


    }// End of measurementChanged()


    public void signUpSubmit() {
        // Error Prevention
        TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        String errorMessage = "";

        // Email
        TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        EditText TextEmail = (EditText) findViewById(R.id.editTextEmail);
        String stringEmail = TextEmail.getText().toString();

        if (stringEmail.isEmpty() || stringEmail.startsWith(" ")) {
            textViewEmail.setTextColor(Color.RED);
            errorMessage = "Please fill in an e-mail address";
        } else {
            textViewEmail.setTextColor(Color.GREEN);
        }
        // Date of Birth Day
        Spinner spinnerDOBDay = (Spinner) findViewById(R.id.spinnerDOBDay);
        String stringDOBDay = spinnerDOBDay.getSelectedItem().toString();
        int intDOBDay = 0;
        try {
            intDOBDay = Integer.parseInt(stringDOBDay);

            if (intDOBDay < 10) {
                stringDOBDay = "0" + stringDOBDay;
            }

        } catch (NumberFormatException e) {
            System.out.println("Could not parse " + e);
            errorMessage = "Please select a day for your birthday.";
        }

        // Date of Birth Month
        Spinner spinnerDOBMonth = (Spinner) findViewById(R.id.spinnerDOBMonth);
        String stringDOBMonth = spinnerDOBMonth.getSelectedItem().toString();
        if (stringDOBMonth.startsWith("Jan")) {
            stringDOBMonth = "01";
        } else if (stringDOBMonth.startsWith("Feb")) {
            stringDOBMonth = "02";
        } else if (stringDOBMonth.startsWith("Feb")) {
            stringDOBMonth = "02";
        } else if (stringDOBMonth.startsWith("Mar")) {
            stringDOBMonth = "03";
        } else if (stringDOBMonth.startsWith("Apr")) {
            stringDOBMonth = "04";
        } else if (stringDOBMonth.startsWith("May")) {
            stringDOBMonth = "05";
        } else if (stringDOBMonth.startsWith("Jun")) {
            stringDOBMonth = "06";
        } else if (stringDOBMonth.startsWith("Jul")) {
            stringDOBMonth = "07";
        } else if (stringDOBMonth.startsWith("Aug")) {
            stringDOBMonth = "08";
        } else if (stringDOBMonth.startsWith("Sep")) {
            stringDOBMonth = "09";
        } else if (stringDOBMonth.startsWith("Oct")) {
            stringDOBMonth = "10";
        } else if (stringDOBMonth.startsWith("Nov")) {
            stringDOBMonth = "11";
        } else if (stringDOBMonth.startsWith("Dec")) {
            stringDOBMonth = "12";
        }

        // Date of Birth Year
        Spinner spinnerDOBYear = (Spinner) findViewById(R.id.spinnerDOBYear);
        String stringDOBYear = spinnerDOBYear.getSelectedItem().toString();
        int intDOBYear = 0;
        try {
            intDOBYear = Integer.parseInt(stringDOBYear);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse " + e);
            errorMessage = "Please select a year for your birthday.";
        }

        // Put date of birth together
        String dateOfBirth = stringDOBMonth + "-" + stringDOBDay + "-" + intDOBYear;


        // Gender
        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        int radioButtonID = radioGroupGender.getCheckedRadioButtonId();
        View radioButtonGender = radioGroupGender.findViewById(radioButtonID);
        int position = radioGroupGender.indexOfChild(radioButtonGender);
        String stringGender = "";
        if (position == 0) {
            stringGender = "male";
        } else {
            stringGender = "female";
        }


        // Height
        EditText editTextHeightCm = (EditText) findViewById(R.id.editTextHeightCm);
        EditText editTextHeightInches = (EditText) findViewById(R.id.editTextHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;
        boolean metric = true;

        //Metric/Imperial
        Spinner spinnerMeasurement = (Spinner) findViewById(R.id.spinnerMeasurement);
        String stringMeasurement = spinnerMeasurement.getSelectedItem().toString();

        int intMeasurement = spinnerMeasurement.getSelectedItemPosition();
        if (intMeasurement == 0) {
            stringMeasurement = "metric";
        } else {
            stringMeasurement = "imperial";
            metric = false;
        }


        if (metric == true) {
            //Convert CM
            try {
                heightCm = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException e) {
                errorMessage = "Height (cm) has to be a number.";
            }

        } else {
            //Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException e) {
                errorMessage = "Height (Feet) has to be a number.";
            }
            //Convert Inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            } catch (NumberFormatException e) {
                errorMessage = "Height (inches) has to be a number.";
            }
            heightCm = ((heightFeet * 12) + heightInches) * 2.54;
            heightCm = (int) heightCm;
        }

        //Weight
        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        String stringWeight = editTextWeight.getText().toString();
        double doubleWeight = 0;
        try {
            doubleWeight = Double.parseDouble(stringWeight);
        } catch (NumberFormatException nfe) {
            errorMessage = "Weight has to be a number.";
        }
        if (metric == true) {
            //Blank if in metric
        } else {
            // Pound to kg
            doubleWeight = Math.round(doubleWeight * 0.45359237);
        }

        //Activity Level
        // 0: Little to no exercise
        // 1: Light exercise (1–3 days per week)
        // 2: Moderate exercise (3–5 days per week)
        // 3: Heavy exercise (6–7 days per week)
        // 4: Very heavy exercise (twice per day, extra heavy workouts)
        Spinner spinnerActivitylevel = (Spinner) findViewById(R.id.spinnerActivityLevel);
        int intActivitylevel = spinnerActivitylevel.getSelectedItemPosition();
        if (errorMessage.isEmpty()) {
            textViewErrorMessage.setVisibility(View.GONE);


            //Sign up information to Database
            DBAdapter db = new DBAdapter(this);
            db.open();

            //quoteSmart
            String stringEmailSQL = db.quoteSmart(stringEmail);
            String dateOfBirthSQL = db.quoteSmart(dateOfBirth);
            String stringGenderSQL = db.quoteSmart(stringGender);
            double heightCmSQL = db.quoteSmart(heightCm);
            int intActivityLevelSQL = db.quoteSmart(intActivitylevel);
            double doubleWeightSQL = db.quoteSmart(doubleWeight);
            String stringMeasurementSQL = db.quoteSmart(stringMeasurement);

            String stringInput = "NULL, " + stringEmailSQL + "," + dateOfBirthSQL + "," + stringGenderSQL + "," + heightCmSQL + "," + intActivityLevelSQL + "," + doubleWeightSQL + "," + stringMeasurementSQL;

            db.insert("users", "user_id, user_email, user_dob, user_gender, user_height, user_activity_level, user_weight, user_measurement",
                    stringInput);

            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String goalDate = df1.format(Calendar.getInstance().getTime());
            String goalDateSQL = db.quoteSmart(goalDate);

            stringInput = "NULL, " + doubleWeightSQL + "," + goalDateSQL;
            db.insert("goal",
                    "goal_id, goal_current_weight, goal_date",
                    stringInput);

            db.close();

            //Move New User to Main Activity
            Intent i = new Intent(SignUp.this, SignUpGoal.class);
            startActivity(i);
        } else {
            textViewErrorMessage.setText(errorMessage);
            textViewErrorMessage.setVisibility(View.VISIBLE);
        }


    }

}
