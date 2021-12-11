package tn.esprit.mylast.utils

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.mylast.models.User

interface ApiInterface {

    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("email")email:String,
        @Field("password")password:String
    ):Call<User>
    @POST("users/register") //methode 2
    @FormUrlEncoded
    fun register(@Field("name")name:String,
                 @Field("email")email:String,
                 @Field("password")password:String
    ):Call<User>

    @PUT("users/update/picture/{id}") //methode 2
    @FormUrlEncoded
    fun updatePicture(@Path("id") id: String?,
                      @Field("picture")picture:String

    ):Call<User>
    @PUT("users/update/picture/{id}")
    @FormUrlEncoded
    fun updateUser(@Path("id") id: String?,
                   @Field("name")name:String,
                   @Field("email")email:String

    ):Call<User>


    companion object {

        var BASE_URL = "http://10.0.2.2:3000/"
       // var BASE_URL ="http://192.168.1.100:3000/"
        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }





  /*  @POST("register") //methode 2
    @FormUrlEncoded
    fun register(@Field("name")name:String,
                 @Field("email")email:String,
                 @Field("password")password:String
                 ):Observable<String>
    @POST("login")
    @FormUrlEncoded
    fun login(
                 @Field("email")email:String,
                 @Field("password")password:String
                 ):Observable<String>*/





}
