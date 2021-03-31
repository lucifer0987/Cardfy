package com.example.cardfy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.example.cardfy.Modals.User;
import com.example.cardfy.R;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    User CurrUser = null;
    ImageView flip, flip_2;
    RelativeLayout step_1, step_2;

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

        Init();
        InitData();

        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step_1.setVisibility(View.GONE);
                step_2.setVisibility(View.VISIBLE);
            }
        });

        flip_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step_1.setVisibility(View.VISIBLE);
                step_2.setVisibility(View.GONE);
            }
        });

    }

    private void Init() {
        flip = findViewById(R.id.flip);
        flip_2 = findViewById(R.id.flip_2);
        step_1 = findViewById(R.id.side_1);
        step_2 = findViewById(R.id.side_2);
    }

    private void InitData() {
        step_1.setVisibility(View.VISIBLE);
        step_2.setVisibility(View.GONE);
    }

}