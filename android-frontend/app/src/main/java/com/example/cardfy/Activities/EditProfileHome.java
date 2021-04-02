package com.example.cardfy.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardfy.Modals.Card;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileHome extends AppCompatActivity {

    ImageView add_pic;
    TextView log_out;
    Uri pickedImgUri;
    String token = "";
    Card CurrCard = null;
    TextView profile_name, personal_info, introduction, story_line, address_info, payment_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_home);

        Init();
        InitValues();

        //selecting profile pic
        add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(EditProfileHome.this);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent i = new Intent(EditProfileHome.this, LogInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(EditProfileHome.this, "You have been logged out.", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileHome.this, EditPersonalInfo.class));
                finish();
            }
        });

        story_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileHome.this, EditStoryLine.class));
                finish();
            }
        });

        payment_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileHome.this, EditPaymentInfo.class));
                finish();
            }
        });

        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileHome.this, EditIntroduction.class));
                finish();
            }
        });

        address_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileHome.this, EditAddressInfo.class));
                finish();
            }
        });

    }

    private void Init() {
        Paper.init(this);
        add_pic = findViewById(R.id.profile_pic);
        log_out = findViewById(R.id.log_out);
        profile_name = findViewById(R.id.profile_name);
        personal_info = findViewById(R.id.personal_info);
        story_line = findViewById(R.id.story_line);
        payment_info = findViewById(R.id.payment_info);
        introduction = findViewById(R.id.introduction);
        address_info = findViewById(R.id.address_info);
    }

    private void InitValues() {
        token = Paper.book().read("token", "");
        CurrCard = Paper.book().read("CurrCard", null);
        Log.e("User", token);
        Log.e("Card", CurrCard.toString());
        Picasso.get().load(CurrCard.getProfileImage()).placeholder(R.drawable.add_pic).into(add_pic);
        profile_name.setText(CurrCard.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Init();
        InitValues();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                pickedImgUri = result.getUri();
                compressImage();
                updateUserInfo();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("this", String.valueOf(error));
            }
        }
    }

    private void compressImage() {
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(pickedImgUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 20, stream);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "image", "image");
        pickedImgUri = Uri.parse(path);
        add_pic.setImageURI(pickedImgUri);
    }

    private void updateUserInfo() {
        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(CurrCard.getProfileImage()));
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_images");
                final StorageReference imageFilePath = mStorage.child(CurrCard.getUsername());

                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //image uploaded successfully
                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(EditProfileHome.this, "Profile pic uploaded", Toast.LENGTH_SHORT).show();
                                updateImageToDatabase(uri);
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception.getMessage().equals("Object does not exist at location.")) {
                    Toast.makeText(EditProfileHome.this, "File Not Found in database!", Toast.LENGTH_SHORT).show();

                    StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_images");
                    //TODO add image file name
                    final StorageReference imageFilePath = mStorage.child(CurrCard.getUsername());

                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //image uploaded successfully
                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(EditProfileHome.this, "Profile pic uploaded", Toast.LENGTH_SHORT).show();
                                    updateImageToDatabase(uri);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(EditProfileHome.this, "Some Error happened. Try again!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateImageToDatabase(Uri uri) {
        CurrCard.setProfileImage(uri.toString());
        Call<Card> call = RetrofitClient.getInstance().getMyApi().putUserData(token, CurrCard);
        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if(response.code() == 200){
                    Toast.makeText(EditProfileHome.this, "Data updated Successfully.", Toast.LENGTH_SHORT).show();
                    GetCard();
                }else{
                    Toast.makeText(EditProfileHome.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Toast.makeText(EditProfileHome.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
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
                } else {
                    Log.e("this", String.valueOf(response.code()));
                    Toast.makeText(EditProfileHome.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.e("this", t.getMessage());
                Toast.makeText(EditProfileHome.this, "Some Failure Occurred!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditProfileHome.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}