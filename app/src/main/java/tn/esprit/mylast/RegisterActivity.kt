package tn.esprit.mylast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.mylast.R
import kotlinx.android.synthetic.main.activity_register.*
import android.content.Intent
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.SharedPreferences

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.models.User
import tn.esprit.mylast.utils.ApiInterface
import com.google.android.material.progressindicator.CircularProgressIndicator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.BackpressureHelper.add
import io.reactivex.schedulers.Schedulers
import android.graphics.Insets.add
import android.view.View
import android.view.WindowManager
import androidx.core.graphics.Insets.add

import tn.esprit.mylast.utils.RetrofitClient

const val PREF_NAME = "LOGIN_PREF_AFFARIET"
class RegisterActivity : AppCompatActivity() {

    private lateinit var Buttonf : Button
    private lateinit var username_et :EditText
    private lateinit var password_et :EditText
    private lateinit var email_et :EditText
    lateinit  var mSharedPref  : SharedPreferences
   // lateinit  var iMyService  : ApiInterface
   // internal var compositeDisposable = CompositeDisposable()
    /*override fun onStop()
    {
        compositeDisposable.clear()
        super.onStop()
    }*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Buttonf = findViewById(R.id.button)
        username_et  = findViewById(R.id.name)
        password_et  = findViewById(R.id.password)
        email_et = findViewById(R.id.email)
        Buttonf.setOnClickListener{
            val username = username_et.text.toString().trim()
            val email = email_et.text.toString().trim()
            val password = password_et.text.toString().trim()
            mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)


            if (username.isEmpty()){
                username_et.error ="User Name Required"
                return@setOnClickListener
                0
            }
            if (username.isEmpty()){
                username_et.error ="User Name Required"
                return@setOnClickListener
0
            }
             if (password.isEmpty()) {
                password_et.error = "password Required"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                email_et.error = "Email Required"
                return@setOnClickListener
            }

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                Toast.makeText(this, "Email is valid", Toast.LENGTH_SHORT).show()

            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
               Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }

            else {

                val name :String = username_et.text.toString()
                val email :String  = email_et.text.toString()
                val password :String  = password_et.text.toString()
                val editor : SharedPreferences.Editor = mSharedPref.edit()
                editor.putString("Name" , name)
                editor.putString("Email" , email)
                editor.putString("Password" , password)
                editor.apply()
                Toast.makeText(this, "Information is saved", Toast.LENGTH_SHORT).show()
                doRegister()

                /*val intent = Intent(this, ProfileActivity::class.java )
                startActivity(intent)
                finish()*/



    }






    }


        btnLogRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

}
 /*   private fun doRegister()
    {
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(ApiInterface::class.java)
        register(username_et.text.toString(),email_et.text.toString() ,password_et.text.toString())

    }

    private fun register(name: String, email: String, password: String) {
        compositeDisposable.add(iMyService.register(name,email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ result -> Toast.makeText(this@RegisterActivity,""+result,Toast.LENGTH_SHORT).show() } )

}*/

 private fun doRegister(){

         val apiInterface = ApiInterface.create()


         apiInterface.register(username_et.text.toString().trim(),email_et.text.toString().trim() ,password_et.text.toString().trim()).enqueue(object :
             Callback<User> {

             override fun onResponse(call: Call<User>, response: Response<User>) {

                 val user = response.body()

                 if (user!=null){
                     Toast.makeText(this@RegisterActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                     startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                 }else{
                     Toast.makeText(this@RegisterActivity, "User already has an account", Toast.LENGTH_SHORT).show()
                 }


             }

             override fun onFailure(call: Call<User>, t: Throwable) {
                 Toast.makeText(this@RegisterActivity, "Connexion error!", Toast.LENGTH_SHORT).show()


             }

         })


 }











}
/*   val name = username_et.text.toString()

               val email = email_et.text.toString()
               val password = password_et.text.toString()

               val intent = Intent(this, LoginActivity::class.java)
               intent.putExtra("Name", name)
               intent.putExtra("Email", email)
               intent.putExtra("Email", password)

              intent.putExtra("Image", uri.toString())
               println("image : " + uri)
               startActivity(intent) */
//picture.setOnClickListener {
//   val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//  intent.type = "image/*"
//  startActivityForResult(intent, 3) }
/*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
        val selectedImage: Uri? = data.data
        val imageView = findViewById<ImageView>(R.id.imageView3)
        imageView.setImageURI(selectedImage)
        uri = selectedImage

    } else {

        Toast.makeText(applicationContext, "You haven't picked Image", Toast.LENGTH_LONG)
            .show();

    }
} */


