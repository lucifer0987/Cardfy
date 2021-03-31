package com.example.cardfy.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cardfy.Modals.SignUpPost;
import com.example.cardfy.Modals.User;
import com.example.cardfy.Modals.UserInfoGet;
import com.example.cardfy.R;
import com.example.cardfy.Retrofit_Interface.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, username, password1, password2;
    Uri pickedImgUri;
    ImageView addpic;
    ImageButton signup, signin;
    ProgressBar pr_sign_up;
    String emailtxt = "", nametxt = "", usernametxt = "", password1txt = "", password2txt = "", image_url_txt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SignUpActivity.this);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setVisibility(View.INVISIBLE);
                pr_sign_up.setVisibility(View.VISIBLE);

                try {
                    emailtxt = email.getText().toString().trim();
                    password1txt = password1.getText().toString();
                    password2txt = password2.getText().toString();
                    nametxt = name.getText().toString().trim();
                    usernametxt = username.getText().toString();

                    if (emailtxt.isEmpty() || nametxt.isEmpty() || password1txt.length() < 8 || password1txt.isEmpty() ||
                            !password1txt.equals(password2txt) || usernametxt.isEmpty() || pickedImgUri == null
                            || usernametxt.indexOf('@') != -1) {
                        if (pickedImgUri == null) {
                            Toast.makeText(SignUpActivity.this, "Select a Profile pic.", Toast.LENGTH_SHORT).show();
                        } else if (password1txt.length() < 8) {
                            Toast.makeText(SignUpActivity.this, "Password should be of length 8 or more", Toast.LENGTH_SHORT).show();
                        } else if (usernametxt.indexOf('@') != -1) {
                            Toast.makeText(SignUpActivity.this, "Username should not contain @", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Please Verify all fields", Toast.LENGTH_SHORT).show();
                        }
                        signup.setVisibility(View.VISIBLE);
                        pr_sign_up.setVisibility(View.INVISIBLE);

                    } else {
                        //everything is ok and fields are filled now we can go to next step
                        uploadImage();
                    }
                } catch (Exception e) {
                    signup.setVisibility(View.VISIBLE);
                    pr_sign_up.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUpActivity.this, "Please enter details in correct format!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadImage() {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_images");
        final StorageReference imageFilePath = mStorage.child(usernametxt);

        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image uploaded successfully
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("this", "Uploaded Successfully!!");
                        image_url_txt = uri.toString();
                        uploadData();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signup.setVisibility(View.VISIBLE);
                pr_sign_up.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void uploadData() {
        SignUpPost post = new SignUpPost(image_url_txt, nametxt, emailtxt, password1txt, usernametxt);
        Call<UserInfoGet> call = RetrofitClient.getInstance().getMyApi().signUpUser(post);
        call.enqueue(new Callback<UserInfoGet>() {
            @Override
            public void onResponse(Call<UserInfoGet> call, Response<UserInfoGet> response) {
                Log.e("this", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(SignUpActivity.this, "Sign Up successfull!! Please Login!!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else if(response.code() == 400){
                    Toast.makeText(SignUpActivity.this, "Some Failure Occured!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                    signup.setVisibility(View.VISIBLE);
                    pr_sign_up.setVisibility(View.INVISIBLE);
                    deleteImage();
                }else if(response.code() == 401){
                    Toast.makeText(SignUpActivity.this, "This Email is already associated with some other account!!", Toast.LENGTH_SHORT).show();
                    signup.setVisibility(View.VISIBLE);
                    pr_sign_up.setVisibility(View.INVISIBLE);
                    deleteImage();
                }else if(response.code() == 405){
                    Toast.makeText(SignUpActivity.this, "Username Taken!! Please use another", Toast.LENGTH_SHORT).show();
                    signup.setVisibility(View.VISIBLE);
                    pr_sign_up.setVisibility(View.INVISIBLE);
                    deleteImage();
                }
            }

            @Override
            public void onFailure(Call<UserInfoGet> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Some Failure Occured!! Please Try Again!!", Toast.LENGTH_SHORT).show();
                signup.setVisibility(View.VISIBLE);
                pr_sign_up.setVisibility(View.INVISIBLE);
                deleteImage();
                Log.e("this", String.valueOf(t.getMessage()));
            }
        });
    }

    private void deleteImage() {
        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(image_url_txt);
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("this", "Deleted Successfully!!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("this", "Some error happened while deleting image\n" + exception.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                pickedImgUri = result.getUri();
                addpic.setImageURI(pickedImgUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("this", String.valueOf(error));
            }
        }
    }

    private void Init() {
        signup = findViewById(R.id.sign_up);
        signin = findViewById(R.id.login);
        addpic = findViewById(R.id.add_pic);
        pr_sign_up = findViewById(R.id.pr_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password1 = findViewById(R.id.pswd1);
        password2 = findViewById(R.id.pswd2);
    }
}