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

public class EditIntroduction extends AppCompatActivity {

    EditText facebook, instagram, twitter, linkedin, whatsapp, email;
    String token = "";
    Card CurrCard = null;
    ImageButton update;
    ProgressBar pr_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_introduction);

        Init();
        InitValues();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pr_update.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);

                CurrCard.setFacebook(facebook.getText().toString().trim());
                CurrCard.setInstagram(instagram.getText().toString().trim());
                CurrCard.setTwitter(twitter.getText().toString().trim());
                CurrCard.setLinkedin(linkedin.getText().toString().trim());
                CurrCard.setWhatsapp("http://wa.me/"+whatsapp.getText().toString().trim());
                CurrCard.setEmail(email.getText().toString().trim());
                CurrCard.setGmail(email.getText().toString().trim());
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
                    Toast.makeText(EditIntroduction.this, "Data updated Successfully.", Toast.LENGTH_SHORT).show();
                    GetCard();
                }else{
                    Toast.makeText(EditIntroduction.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                    pr_update.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditIntroduction.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EditIntroduction.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("this", t.getMessage());
                pr_update.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                Toast.makeText(EditIntroduction.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeActivity() {
        Intent i = new Intent(EditIntroduction.this, EditProfileHome.class);
        startActivity(i);
    }

    private void InitValues() {
        Paper.init(this);
        CurrCard = Paper.book().read("CurrCard", null);
        token = Paper.book().read("token", "");

        facebook.setText(CurrCard.getFacebook());
        instagram.setText(CurrCard.getInstagram());
        twitter.setText(CurrCard.getTwitter());
        linkedin.setText(CurrCard.getLinkedin());
        String whats = CurrCard.getWhatsapp();
        if(whats.equals("")){
            whatsapp.setText(whats);
        }else{
            whatsapp.setText(CurrCard.getWhatsapp().substring(13));
        }
        email.setText(CurrCard.getEmail());
    }

    private void Init() {
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);
        linkedin = findViewById(R.id.linkedin);
        whatsapp = findViewById(R.id.whatsapp);
        email = findViewById(R.id.email);

        update = findViewById(R.id.update);
        pr_update = findViewById(R.id.pr_update);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditIntroduction.this, EditProfileHome.class);
        startActivity(i);
    }
}