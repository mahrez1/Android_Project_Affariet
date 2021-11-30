package tn.esprit.mylast

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.mylast.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.MainActivity
import tn.esprit.mylast.models.User
import tn.esprit.mylast.utils.ApiInterface
import tn.esprit.mylast.utils.RetrofitClient

class LoginActivity : AppCompatActivity() {
    private lateinit var Button : Button
    private lateinit var email_et :EditText
    private lateinit var password_et :EditText
   // lateinit  var iMyService  : ApiInterface
   // internal var compositeDisposable = CompositeDisposable()
    /*override fun onStop()
    {
        compositeDisposable.clear()
        super.onStop()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Button = findViewById(R.id.buttonl)

        password_et  = findViewById(R.id.password)
        email_et = findViewById(R.id.email)

        Button.setOnClickListener{

            val email = email_et.text.toString().trim()
            val password = password_et.text.toString().trim()


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
                val intent = Intent(this, LoginActivity::class.java);
                intent.putExtra("data","test data")
                startActivity(intent)

            }
           if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }

           else{
              // startActivity(Intent(this,LoginActivity::class.java))
               dologin()


            }
           // else{startActivity(Intent(this,MainActivity::class.java))}

        }

       // buttonl.setOnClickListener(){startActivity(Intent(this, MainActivity::class.java))}

        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
    }
    /*private fun dologin()
    {
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(ApiInterface::class.java)
        login(email_et.text.toString() ,password_et.text.toString())

    }

    private fun login( email: String, password: String) {
        compositeDisposable.add(iMyService.login(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ result -> Toast.makeText(this@LoginActivity,""+result,Toast.LENGTH_SHORT).show()

            } )

    }*/

    private fun dologin(){

        val apiInterface = ApiInterface.create()


        apiInterface.login(email_et.text.toString() ,password_et.text.toString()).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null)
                {
                    Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })}





}
