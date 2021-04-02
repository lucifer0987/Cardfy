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

public class EditStoryLine extends AppCompatActivity {

    EditText intro, short_desc, long_desc, fund_desc;
    String token = "";
    Card CurrCard = null;
    ImageButton update;
    ProgressBar pr_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story_line);

        Init();
        InitValues();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pr_update.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);

                CurrCard.setIntro(intro.getText().toString().trim());
                CurrCard.setShortDesc(short_desc.getText().toString().trim());
                CurrCard.setLongDesc(long_desc.getText().toString().trim());
                CurrCard.setFundDesc(fund_desc.getText().toString().trim());
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
                    Toast.makeText(EditStoryLine.this, "Data updated Successfully.", Toast.LENGTH_SHORT).show();
                    GetCard();
                }else{
                    Toast.makeText(EditStoryLine.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                    pr_update.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditStoryLine.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EditStoryLine.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("this", t.getMessage());
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditStoryLine.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeActivity() {
        Intent i = new Intent(EditStoryLine.this, EditProfileHome.class);
        startActivity(i);
    }

    private void InitValues() {
        Paper.init(this);
        CurrCard = Paper.book().read("CurrCard", null);
        token = Paper.book().read("token", "");
        intro.setText(CurrCard.getIntro());
        short_desc.setText(CurrCard.getShortDesc());
        long_desc.setText(CurrCard.getLongDesc());
        fund_desc.setText(CurrCard.getFundDesc());
    }

    private void Init() {
        intro = findViewById(R.id.intro);
        short_desc = findViewById(R.id.short_desc);
        long_desc = findViewById(R.id.long_desc);
        fund_desc = findViewById(R.id.fund_desc);

        update = findViewById(R.id.update);
        pr_update = findViewById(R.id.pr_update);
    }

    @Override
    public void onBackPressed() {
        changeActivity();
    }
}