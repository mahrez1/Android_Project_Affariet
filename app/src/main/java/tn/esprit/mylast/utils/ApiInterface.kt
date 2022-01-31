package tn.esprit.mylast.utils

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.models.User
import java.util.HashMap

interface ApiInterface {
    data class SearchResponse(
        @SerializedName("lots")
        val users: MutableList<Lot>,
    )
    data class SearchBody(val localisation: String)
   // data class updateResponse(@SerializedName("user") val user: User)

  //  data class GetUserBody(val id: String)

   // @get:GET("lot/show/all/")
   // val posts : Call<MutableList<Lot>>

    @GET("users/show/one/{id}") //methode 2
    fun getOne(
        @Path("id") id: String?

    ):Call<User>

   @GET("users/lot/show/all/")
   fun get(): Call<MutableList<Lot?>?>?


    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ):Call<User>
    @POST("users/register") //methode 2
    @FormUrlEncoded
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ):Call<User>

    @PUT("users/update/picture/{id}") //methode 2
    @FormUrlEncoded
    fun updatePicture(
        @Path("id") id: String?,
        @Field("picture") picture: String

        ):Call<User>
    @PUT("users/update/{id}")
    @FormUrlEncoded
    fun updateUser(
        @Path("id") id: String?,
        @Field("name") name: String,
        @Field("email") email: String,

        ):Call<User>
    @Multipart
    @POST("users/lot/postuler/oo") //methode 2
    fun addPost(
        @Part("localisation") localisation: String,
        @Part("description") description: String,
        @Part("price") price: String,
        @Part image: MultipartBody.Part?,
        @Part("image") imageName: RequestBody?,
    ): io.reactivex.rxjava3.core.Flowable<ResponseBody>

    @POST("users/lot/search/")
    fun search(@Body text: SearchBody): Call<SearchResponse>



    @FormUrlEncoded
    @POST("users/lot/postuler")
    fun upload(
        @Field("localisation") localisation: String,
        @Field("description") description: String,
        @Field("price") price: String,
        @Field("contact") contact: String,
        @Field("picture") image: String

        ): Call<Lot>



   /* @Multipart
    @POST("posts/")
    fun addPost(
        @Part("idUser") idUser: RequestBody?,
        @Part("idCategory") idCategory: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("image") imageName: RequestBody?,
    ): io.reactivex.rxjava3.core.Flowable<ResponseBody?>?*/

    companion object {

       // var BASE_URL = "http://10.0.2.2:3000/"
        var BASE_URL ="http://192.168.106.1:3000/"
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
