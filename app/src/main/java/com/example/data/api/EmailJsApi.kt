package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class EmailJsRequest(
    @Json(name = "service_id") val serviceId: String,
    @Json(name = "template_id") val templateId: String,
    @Json(name = "user_id") val userId: String,
    @Json(name = "template_params") val templateParams: Map<String, String>
)

interface EmailJsApi {
    @POST("api/v1.0/email/send")
    suspend fun sendEmail(@Body request: EmailJsRequest): retrofit2.Response<okhttp3.ResponseBody>
}
