package tn.esprit.mylast.utils

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var instance: Retrofit?=null
    fun getInstance():Retrofit
    {
        if(instance==null)
            instance=Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return instance!!

    }


}
/*  @POST("login")
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
    }*/