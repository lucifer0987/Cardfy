package com.example.cardfy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cardfy.Modals.User;
import com.example.cardfy.R;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    User CurrUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        CurrUser = Paper.book().read("CurrUser");
        if(CurrUser == null){
            Intent i = new Intent(MainActivity.this, LogInActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }
}