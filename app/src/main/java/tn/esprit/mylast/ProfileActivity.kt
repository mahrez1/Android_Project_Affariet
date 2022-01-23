package tn.esprit.mylast
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
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.Result
import androidx.appcompat.view.menu.MenuView.ItemView as ItemView1


class ProfileActivity : AppCompatActivity() {
    private lateinit var Buttonhome : Button
    private var uri: Uri? = null
    lateinit  var sharedPref  : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Buttonhome = findViewById(R.id.btnhome)

        val picture = findViewById<ImageView>(R.id.imageView5)
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

        picture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 3) }

        stng.setOnClickListener {
            val intent = Intent(this,Setting::class.java)
            startActivity(intent)
        }


        btnhome.setOnClickListener{navigate() }

        backBytton.setOnClickListener {
            onBackPressed()
        }



    }
    private fun navigate(){
        val intent = Intent(this, MainActivity::class.java)
        // intent.putExtra("Image", uri.toString())
        println("image : " + uri)
        startActivity(intent)

    }







    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            val imageView = findViewById<ImageView>(R.id.imageView5)
            imageView.setImageURI(selectedImage)
            uri = selectedImage

            sharedPref.edit().putString("image",uri.toString()).apply()
            Log.i("image",uri.toString())

        } else {


            Toast.makeText(applicationContext, "You haven't picked Image", Toast.LENGTH_LONG)
                .show();

        }
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