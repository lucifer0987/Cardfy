package com.example.cardfy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cardfy.Modals.LoginUserGet;
import com.example.cardfy.Modals.LoginUserPost;
import com.example.cardfy.Modals.User;
import com.example.cardfy.Modals.UserInfoGet;
import com.example.cardfy.R;
import com.example.cardfy.Retrofit_Interface.RetrofitClient;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private AppCompatButton sign_in;
    private ImageButton sign_up;
    private ProgressBar pr_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        Init();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pr_sign_in.setVisibility(View.VISIBLE);
                sign_in.setVisibility(View.INVISIBLE);
                //Logging In
                LoginUserPost curr = new LoginUserPost(email.getText().toString().trim(), password.getText().toString().trim());
                Call<LoginUserGet> call = RetrofitClient.getInstance().getMyApi().loginUser(curr);
                call.enqueue(new Callback<LoginUserGet>() {
                    @Override
                    public void onResponse(Call<LoginUserGet> call, Response<LoginUserGet> response) {
                        if(response.code() == 200){
                            Toast.makeText(LogInActivity.this, "Log In Successful!!", Toast.LENGTH_LONG).show();
                            String token = response.body().getToken();

                            //Getting USER INFO
                            Call<UserInfoGet> call2 = RetrofitClient.getInstance().getMyApi().getUserInfo(token);
                            call2.enqueue(new Callback<UserInfoGet>() {
                                @Override
                                public void onResponse(Call<UserInfoGet> call, Response<UserInfoGet> response) {
                                    if(response.code() == 200){
                                        UserInfoGet result = response.body();

                                        User curr = new User(token, result.getUsername(), result.getEmail(), result.getPassword(), result.getName(), result.isVarified(), result.getProfile_image());
                                        Log.e("this", curr.toString());
                                        Paper.book().destroy();
                                        Paper.book().write("CurrUser", curr);

                                        Intent i = new Intent(LogInActivity.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    }else{
                                        pr_sign_in.setVisibility(View.INVISIBLE);
                                        sign_in.setVisibility(View.VISIBLE);
                                        Toast.makeText(LogInActivity.this, "Some Failure Occured!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserInfoGet> call, Throwable t) {
                                    pr_sign_in.setVisibility(View.INVISIBLE);
                                    sign_in.setVisibility(View.VISIBLE);
                                    Toast.makeText(LogInActivity.this, "Some Failure Occured!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if(response.code() == 400){
                            Toast.makeText(LogInActivity.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                            pr_sign_in.setVisibility(View.INVISIBLE);
                            sign_in.setVisibility(View.VISIBLE);
                        }else if(response.code() == 404){
                            Toast.makeText(LogInActivity.this, "Email Not Registered!!", Toast.LENGTH_LONG).show();
                            pr_sign_in.setVisibility(View.INVISIBLE);
                            sign_in.setVisibility(View.VISIBLE);
                        }else if(response.code() == 403){
                            Toast.makeText(LogInActivity.this, "Wrong Password!!", Toast.LENGTH_LONG).show();
                            pr_sign_in.setVisibility(View.INVISIBLE);
                            sign_in.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginUserGet> call, Throwable t) {
                        pr_sign_in.setVisibility(View.INVISIBLE);
                        sign_in.setVisibility(View.VISIBLE);
                        Toast.makeText(LogInActivity.this, "Some Failure Occured!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                        Log.e("this", String.valueOf(t.getMessage()));
                    }
                });
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    private void Init() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        pr_sign_in = findViewById(R.id.pr_sign_in);
    }
}