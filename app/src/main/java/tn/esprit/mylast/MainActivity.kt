package tn.esprit.mylast
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.mylast.databinding.ActivityMainBinding
import android.content.Intent
import android.content.SharedPreferences
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import android.view.LayoutInflater

class MainActivity : AppCompatActivity() {
    lateinit  var sharedPreff  : SharedPreferences
    private var uri: Uri? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header_main)
        setContentView(R.layout.fragment_home)
        setContentView(R.layout.terrain_single_item)
//        setContentView(R.layout.fragment_gallery)



        //  val intent = intent
        // val img = intent.getStringExtra("Image")
        // val picBundle = Uri.parse(img).toString()

        sharedPreff = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val bundle = intent.getBundleExtra("bundle")
        val nameBundle = sharedPreff.getString("Name","")
        val emailBundle = sharedPreff.getString("Email","")




        changeFragment(
            Result.newInstance(nameBundle, emailBundle, ),
            ""
        )








        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.message,R.id.pannier
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //picture.setOnClickListener {    navigate()}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun navigate(){
        val mainIntent = Intent(this, ProfileActivity::class.java)
        startActivity(mainIntent)
    }

    private fun changeFragment(fragment: Fragment, name: String) {
        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.nav_view, fragment)
                .commit()
        else {
            supportFragmentManager.beginTransaction().replace(R.id.nav_view, fragment)
                .addToBackStack(name).commit()
        }
    }

}




// val name = sharedPreff.getString("Name","")
//   val email = sharedPreff.getString("Email","")
//   val username = findViewById<TextView>(R.id.usernamep)
//   username.text = name
//  val usermail = findViewById<TextView>(R.id.usermailp)
//  usermail.text = email
