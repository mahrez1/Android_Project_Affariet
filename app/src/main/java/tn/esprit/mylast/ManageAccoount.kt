package tn.esprit.mylast

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_accoount.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.models.User
import tn.esprit.mylast.utils.ApiInterface


class ManageAccoount : AppCompatActivity() {
    private lateinit var name_et : EditText
    private lateinit var email_et : EditText
    private lateinit var Buttonm : Button
    lateinit  var sharedPref  : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_accoount)
        name_et  = findViewById(R.id.edit_name)
        email_et = findViewById(R.id.edit_email)
        Buttonm= findViewById(R.id.btnUp)
       // sharedPref = getSharedPreferences(PREF_NAMEE, MODE_PRIVATE)
        //val id = sharedPref.getString("Id","")

        var amine : Boolean = true
        var youssef : Boolean = true



        Upd_info.setOnClickListener {

            if (youssef == true) {
                info.visibility =View.VISIBLE
                youssef = false

            }
            else {
                info.visibility =View.GONE
                youssef = true

            }


        }
        btnUp.setOnClickListener {doUpdateUserProfile() }

        backBytton.setOnClickListener {
            onBackPressed()
        }

    }
    private fun doUpdateUserProfile(){

        val apiInterface = ApiInterface.create()
       // sharedPref = getSharedPreferences(PREF_NAMEE, MODE_PRIVATE)
        sharedPref = getSharedPreferences(PREF_NAMEE,MODE_PRIVATE)
        val em=sharedPref.getString("EMAIL","")
        val sh=sharedPref.getString("ID","")
        if (sh != null) {
            Log.i("hhh", em.toString())
        }
        apiInterface.updateUser(id =sh ,name_et.text.toString().trim(),email_et.text.toString().trim()).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {


                val user = response.body()

                if (user!=null){
                    Toast.makeText(this@ManageAccoount, "Update Success", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this@ManageAccoount, LoginActivity::class.java))

                }else{
                    Toast.makeText(this@ManageAccoount, "Error", Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ManageAccoount, "Connexion error!", Toast.LENGTH_SHORT).show()


            }

        })


    }


}