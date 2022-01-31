package tn.esprit.mylast.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.nav_header_main.*
import tn.esprit.mylast.LoginActivity
import tn.esprit.mylast.MapsActivity
import tn.esprit.mylast.R
import tn.esprit.mylast.data.CATEGORIE
import tn.esprit.mylast.data.DESCRIPTION
import tn.esprit.mylast.data.PICTURE
import tn.esprit.mylast.models.contact
import tn.esprit.mylast.models.description
import tn.esprit.mylast.models.localisation
import tn.esprit.mylast.models.picture

class DetailActivity : AppCompatActivity() {
    lateinit var terr_pic : ImageView
    lateinit var terr_cat : TextView
    lateinit var terr_desc: TextView
    lateinit var btn_map: Button
    lateinit var terr_ct: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        btn_map = findViewById(R.id.btnmap)


        terr_pic = findViewById(R.id.terrainPic)
        terr_cat = findViewById(R.id.categorie)
        terr_desc = findViewById(R.id.description)
        terr_ct = findViewById(R.id.conn)

        val g = intent.getStringExtra(picture)
        Log.i("emchi","ggggggg"+g)
         val b =g?.toUri()
        Picasso.with(applicationContext).load(b).into(terr_pic)



        val categorie = intent.getStringExtra(localisation)
        val description = intent.getStringExtra(description)
        val contact = intent.getStringExtra(contact)

        title= "$description Detail"

        terr_cat.text = "Localisation: $categorie"
        terr_desc.text = "Description : $description"
        terr_ct.text = "Contact : $contact"

        btn_map.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java)) }

    }
}