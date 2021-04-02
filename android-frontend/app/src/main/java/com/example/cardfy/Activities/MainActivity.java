package com.example.cardfy.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardfy.Modals.Card;
import com.example.cardfy.Modals.User;
import com.example.cardfy.R;
import com.example.cardfy.Retrofit_Interface.RetrofitClient;
import com.squareup.picasso.Picasso;

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
    ImageView profile_pic;
    TextView profile_name, intro, short_desc, long_desc;
    ImageView is_verified, facebook, instagram, whatsapp, twitter, linkedin, gmail;
    RatingBar rating, rating2;
    ProgressBar pr_main;
    TextView address, fund_desc;
    ImageView upi, gpay, paytm, phonepe, discord, slack, youtube, skype;

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

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(facebook, CurrCard.getFacebook());
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(whatsapp, CurrCard.getWhatsapp());
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(twitter, CurrCard.getTwitter());
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(linkedin, CurrCard.getLinkedin());
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrCard.getEmail().equals("")) {
                    Toast.makeText(MainActivity.this, "Not Provided!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{CurrCard.getEmail()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Enter your subject here!");
                    intent.putExtra(Intent.EXTRA_TEXT, "Enter your message here!");
                    startActivity(Intent.createChooser(intent, "Choose application"));
                }
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(instagram, CurrCard.getInstagram());
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.co.in/maps?q=" + CurrCard.getAddressSlug();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(CurrCard.getUpi());
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(CurrCard.getPaytm());
            }
        });

        phonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(CurrCard.getPpay());
            }
        });

        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog(CurrCard.getGpay());
            }
        });

        discord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(discord, CurrCard.getDiscord());
            }
        });

        slack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(slack, CurrCard.getSlack());
            }
        });

        skype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(skype, CurrCard.getSkype());
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(youtube, CurrCard.getYoutube());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Init();
        GetCard();
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
        pr_main = findViewById(R.id.pr_main);
        step_1.setVisibility(View.INVISIBLE);
        step_2.setVisibility(View.GONE);
        pr_main.setVisibility(View.VISIBLE);

        edit_profile = findViewById(R.id.edit_profile);
        share_profile = findViewById(R.id.share_profile);

        profile_pic = findViewById(R.id.profile_pic);
        profile_name = findViewById(R.id.profile_name);
        is_verified = findViewById(R.id.verified_tag);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);
        linkedin = findViewById(R.id.linkedin);
        whatsapp = findViewById(R.id.whatsapp);
        gmail = findViewById(R.id.gmail);
        intro = findViewById(R.id.profession);
        short_desc = findViewById(R.id.description);
        long_desc = findViewById(R.id.story_line);
        rating = findViewById(R.id.rating_bar);
        rating2 = findViewById(R.id.rating_bar_2);
        address = findViewById(R.id.address);
        fund_desc = findViewById(R.id.praise_story);

        upi = findViewById(R.id.upi);
        gpay = findViewById(R.id.gpay);
        paytm = findViewById(R.id.paytm);
        phonepe = findViewById(R.id.phonepe);

        discord = findViewById(R.id.discord);
        slack = findViewById(R.id.slack);
        youtube = findViewById(R.id.youtube);
        skype = findViewById(R.id.skype);
    }

    private void InitData() {
        Picasso.get().load(CurrCard.getProfileImage()).placeholder(R.drawable.add_pic).into(profile_pic);
        setText(CurrCard.getName(), profile_name);
        if (CurrCard.isIsVerified()) {
            is_verified.setVisibility(View.VISIBLE);
        } else {
            is_verified.setVisibility(View.GONE);
        }
        setText(CurrCard.getIntro(), intro);
        setText(CurrCard.getShortDesc(), short_desc);
        setText(CurrCard.getLongDesc(), long_desc);
        rating.setRating(CurrCard.getRating());
        rating2.setRating(CurrCard.getRating());
        setText(CurrCard.getAddress()+", "+CurrCard.getCity()+", "+CurrCard.getState()+", "+CurrCard.getCountry(), address);
        setText(CurrCard.getFundDesc(), fund_desc);

        step_1.setVisibility(View.VISIBLE);
        step_2.setVisibility(View.GONE);
        pr_main.setVisibility(View.GONE);
    }

    private void setText(String text, TextView tv) {
        if (text.equals("")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    private void performClick(ImageView iv, String url) {
        if (url.equals("")) {
            Toast.makeText(this, "Not Provided!!", Toast.LENGTH_SHORT).show();
        } else if (!url.startsWith("http:")) {
            Toast.makeText(this, "Invalid Profile given", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void showPaymentDialog(String upi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Enter Amount");

        final EditText input = new EditText(MainActivity.this);
        input.setHint("Enter Amount!!");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        final String[] amount = {""};
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                amount[0] = input.getText().toString();
                launchUPI(upi, amount[0]);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void launchUPI(String upi_id, String amount) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upi_id)
                .appendQueryParameter("pn", CurrCard.getName())
                .appendQueryParameter("tn", "Received with Cardfy!!")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        startActivity(Intent.createChooser(upiPayIntent, "Pay with"));
    }

}