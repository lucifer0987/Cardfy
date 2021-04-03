package com.example.cardfy.Retrofit_Interface;

import com.example.cardfy.Modals.Card;
import com.example.cardfy.Modals.LoginUserGet;
import com.example.cardfy.Modals.LoginUserPost;
import com.example.cardfy.Modals.SignUpPost;
import com.example.cardfy.Modals.UserInfoGet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Api {
    String BASE_URL = "https://cardfy.herokuapp.com/api/";

    @POST("users/login")
    Call<LoginUserGet> loginUser(@Body LoginUserPost loginUserPost);

    @GET("users/me")
    Call<UserInfoGet> getUserInfo(@Header("x-auth-token") String token);

    @POST("users")
    Call<UserInfoGet> signUpUser(@Body SignUpPost signUpPost);

    @GET("card/me")
    Call<Card> getUserCard(@Header("x-auth-token") String token);

    @PUT("card/me")
    Call<Card> putUserData(@Header("x-auth-token") String token, @Body Card post);

}
