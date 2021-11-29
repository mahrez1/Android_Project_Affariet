package tn.esprit.mylast.utils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import tn.esprit.mylast.models.User

interface ApiInterface {

    @POST("login")
    fun login(@Query("email") email: String, @Query("password") password: String): Call<User>
    @POST("register")
    fun register(@Query("name") name: String,@Query("email") email: String, @Query("password") password: String): Call<User>

    companion object {

        var BASE_URL = "http://10.0.2.2:3000/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}