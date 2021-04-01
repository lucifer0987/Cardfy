package com.example.cardfy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cardfy.Modals.Card;
import com.example.cardfy.Modals.User;
import com.example.cardfy.R;
import com.example.cardfy.Retrofit_Interface.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String token = "";
    Card CurrCard = null;
    ImageView flip, flip_2, edit_profile, share_profile;
    RelativeLayout step_1, step_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        token = Paper.book().read("token", "");

        Init();

        if (token.equals("")) {
            Intent i = new Intent(MainActivity.this, LogInActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            GetCard();
        }

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

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditProfileHome.class);
                startActivity(i);
            }
        });

        share_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShareProfile.class);
                startActivity(i);
            }
        });

    }

    private void GetCard() {
        Call<Card> call = RetrofitClient.getInstance().getMyApi().getUserCard(token);
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.code() == 200) {
                    CurrCard = response.body();
                    Log.e("this", CurrCard.toString());
                    Paper.book().write("CurrCard", CurrCard);
                    InitData();
                } else {
                    Log.e("this", String.valueOf(response.code()));
                    Toast.makeText(MainActivity.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("this", t.getMessage());
                Toast.makeText(MainActivity.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Init() {
        flip = findViewById(R.id.flip);
        flip_2 = findViewById(R.id.flip_2);
        step_1 = findViewById(R.id.side_1);
        step_2 = findViewById(R.id.side_2);
        step_1.setVisibility(View.VISIBLE);
        step_2.setVisibility(View.GONE);

        edit_profile = findViewById(R.id.edit_profile);
        share_profile = findViewById(R.id.share_profile);
    }

    private void InitData() {

    }

}