package tn.esprit.mylast
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import tn.esprit.mylast.R
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.view.menu.MenuView.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.models.User
import tn.esprit.mylast.utils.ApiInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Result
import androidx.appcompat.view.menu.MenuView.ItemView as ItemView1


class ProfileActivity : AppCompatActivity() {
    private lateinit var Buttonhome : Button
    private lateinit var pickImage: Button

    private var uri: Uri? = null
    lateinit  var sharedPref  : SharedPreferences
    val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formater.format(now)
    private var selectedImageUri: Uri? = null
    private lateinit var upload: Button
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Buttonhome = findViewById(R.id.btnhome)
        upload = findViewById(R.id.upload)
        pickImage = findViewById(R.id.pickImage)


         imageView = findViewById(R.id.imageView5)
        sharedPref = getSharedPreferences(PREF_NAMEE, MODE_PRIVATE)
        val name = sharedPref.getString("NAME","")
        val email = sharedPref.getString("EMAIL","")
        Log.i("holo","aaaaaaaaaaaa"+sharedPref.getString("NAME",""))

        val username = findViewById<TextView>(R.id.username)
        username.text = name
        val stng = findViewById<FrameLayout>(R.id.settingBytton)

        val usermail = findViewById<TextView>(R.id.usermail)
        usermail.text = email


        // val intent = Intent(this, MainActivity::class.java)

        //  startActivity(intent)

        pickImage.setOnClickListener {
            openGallery()
        }
        upload.setOnClickListener{
            uploadImage()
        }

        stng.setOnClickListener {
            val intent = Intent(this,Setting::class.java)
            startActivity(intent)
        }


        btnhome.setOnClickListener{navigate()
            doUpdatePicture()
        }

        backBytton.setOnClickListener {
            onBackPressed()
        }



    }

    private fun uploadImage()
    {
        if (selectedImageUri == null) {
            Toast.makeText(this@ProfileActivity,"Please Select Picture", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading Image ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val storageReference = FirebaseStorage.getInstance().reference.child("uploads/$fileName")
            storageReference.putFile(selectedImageUri!!).
            addOnSuccessListener {
                imageView!!.setImageURI(selectedImageUri)
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Successfuly uploaded", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Sorry", Toast.LENGTH_SHORT).show()

            }

        }}



     fun navigate(){
        val intent = Intent(this, MainActivity::class.java)
        // intent.putExtra("Image", uri.toString())
        println("image : " + uri)
        startActivity(intent)

    }







        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == 100 && resultCode == RESULT_OK)
            {
                selectedImageUri = data?.data!!
                imageView.setImageURI(selectedImageUri)
            }
        }
    private fun openGallery() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,100)
        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.logout ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.logoutTitle))
                builder.setMessage(R.string.logoutMessage)
                builder.setPositiveButton("Yes"){ dialogInterface, which ->
                    getSharedPreferences(PREF_NAMEE, MODE_PRIVATE).edit().clear().apply()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun doUpdatePicture(){

        val apiInterface = ApiInterface.create()
        sharedPref = getSharedPreferences(PREF_NAMEE,MODE_PRIVATE)
        val sh=sharedPref.getString("ID","")

        apiInterface.updatePicture(id =sh ,fileName).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {


                Toast.makeText(this@ProfileActivity, "update Success", Toast.LENGTH_SHORT).show()





            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()


            }

        })


    }

}


/*   title = "Your Resume"
        /**
         * Initialize the intent
         */
        val intent = intent

        /**
         * Call the vals from the previous intent to get their data
         */
        val name = intent.getStringExtra("Name")
        val email = intent.getStringExtra("Email")
        val img= intent.getStringExtra("Image")
        val fileUri = Uri.parse(img)

        val username = findViewById<TextView>(R.id.username)
        val image=findViewById<ImageView>(R.id.imageView5)
        username.text = name
        val usermail = findViewById<TextView>(R.id.usermail)
        usermail.text = email
        image.setImageURI(fileUri) */