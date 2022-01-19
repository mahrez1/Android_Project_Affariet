package tn.esprit.mylast.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import tn.esprit.mylast.R
import tn.esprit.mylast.data.CATEGORIE
import tn.esprit.mylast.data.DESCRIPTION
import tn.esprit.mylast.data.PICTURE
import tn.esprit.mylast.models.description
import tn.esprit.mylast.models.localisation
import tn.esprit.mylast.models.picture

class DetailActivity : AppCompatActivity() {
    lateinit var terr_pic : ImageView
    lateinit var terr_cat : TextView
    lateinit var terr_desc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        terr_pic = findViewById(R.id.terrainPic)
        terr_cat = findViewById(R.id.categorie)
        terr_desc = findViewById(R.id.description)
       val g = intent.getStringExtra(picture)
        Log.i("emchi","ggggggg"+g)
         val b =g?.toUri()

        println(  terr_pic.setImageURI(b))

        val categorie = intent.getStringExtra(localisation)
        val description = intent.getStringExtra(description)

        title= "$description Detail"

        terr_cat.text = "Localisation: $categorie"
        terr_desc.text = "Description : $description"

    }
}