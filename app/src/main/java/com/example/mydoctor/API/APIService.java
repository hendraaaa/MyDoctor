package com.example.mydoctor.API;

import com.example.mydoctor.Notification.MyResponse;
import com.example.mydoctor.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAoAtpadQ:APA91bG8tCd_bjTs9aheMysQSpzibirHQGJKBkyLkIbiofXfw9KU93o8eyu5aKZ0Gl6fzDLHaBLn9VKNO8EmlZ9fsZKWNZRHvprTF8z4M6BnZA_EkSNM7brbtV8at9tnjLaf10rbXpAT"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
