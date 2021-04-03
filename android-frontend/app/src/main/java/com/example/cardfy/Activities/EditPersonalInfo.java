package com.example.cardfy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cardfy.Modals.Card;
import com.example.cardfy.R;
import com.example.cardfy.Retrofit_Interface.RetrofitClient;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPersonalInfo extends AppCompatActivity {

    EditText discord, slack, youtube, skype;
    String token = "";
    Card CurrCard = null;
    ImageButton update;
    ProgressBar pr_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        Init();
        InitValues();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pr_update.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);

                CurrCard.setDiscord(discord.getText().toString().trim());
                CurrCard.setSlack(slack.getText().toString().trim());
                CurrCard.setYoutube(youtube.getText().toString().trim());
                CurrCard.setSkype(skype.getText().toString().trim());
                updateDB();
            }
        });

    }

    private void updateDB() {
        Call<Card> call = RetrofitClient.getInstance().getMyApi().putUserData(token, CurrCard);
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if(response.code() == 200){
                    Toast.makeText(EditPersonalInfo.this, "Data updated Successfully.", Toast.LENGTH_SHORT).show();
                    GetCard();
                }else{
                    Toast.makeText(EditPersonalInfo.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                    pr_update.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditPersonalInfo.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
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
                    changeActivity();
                } else {
                    pr_update.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                    Log.e("this", String.valueOf(response.code()));
                    Toast.makeText(EditPersonalInfo.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("this", t.getMessage());
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditPersonalInfo.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeActivity() {
        Intent i = new Intent(EditPersonalInfo.this, EditProfileHome.class);
        startActivity(i);
    }

    private void InitValues() {
        Paper.init(this);
        CurrCard = Paper.book().read("CurrCard", null);
        token = Paper.book().read("token", "");
        discord.setText(CurrCard.getDiscord());
        slack.setText(CurrCard.getSlack());
        youtube.setText(CurrCard.getYoutube());
        skype.setText(CurrCard.getSkype());
    }

    private void Init() {
        discord = findViewById(R.id.discord);
        slack = findViewById(R.id.slack);
        youtube = findViewById(R.id.youtube);
        skype = findViewById(R.id.skype);

        update = findViewById(R.id.update);
        pr_update = findViewById(R.id.pr_update);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditPersonalInfo.this, EditProfileHome.class);
        startActivity(i);
    }
}