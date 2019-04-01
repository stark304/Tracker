package com.example.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Stetho (http://facebook.github.io/stetho/)
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        //Database
        DBAdapter db = new DBAdapter(this);
        db.open();
        int rows = db.count("food");

        if(rows < 1)
        {
            Toast.makeText(this, "Setup, Good and Live",Toast.LENGTH_LONG).show();
            DBAdapter setupInsert = new DBAdapter(this);
            setupInsert.insertAllCategories();
            setupInsert.insertAllFood();

        }

       Toast.makeText(this,"Database works!, Food Table Has Been Created",Toast.LENGTH_SHORT).show();
        //Check is User exist?
        // Count rows in user table
        int userrows = db.count("users");
        if(userrows <1)
        {
            Toast.makeText(this, "You are only few fields away from signing up...", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
        }
    }


}
