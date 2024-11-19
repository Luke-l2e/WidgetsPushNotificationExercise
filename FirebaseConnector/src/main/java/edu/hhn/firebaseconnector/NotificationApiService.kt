package edu.hhn.firebaseconnector

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApiService {
    @POST("sendNotification")
    fun sendNotification(@Body request: NotificationRequest): Call<NotificationResponse>
}