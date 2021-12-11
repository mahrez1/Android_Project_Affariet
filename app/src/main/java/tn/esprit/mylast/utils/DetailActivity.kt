package tn.esprit.mylast.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.mylast.R
import tn.esprit.mylast.data.CATEGORIE
import tn.esprit.mylast.data.DESCRIPTION
import tn.esprit.mylast.data.PICTURE

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

        terr_pic.setImageResource(intent.getIntExtra(PICTURE, 0))

        val categorie = intent.getStringExtra(CATEGORIE)
        val description = intent.getStringExtra(DESCRIPTION)

        title= "$categorie Detail"

        terr_cat.text = "Categorie: $categorie"
        terr_desc.text = "Desrpt : $description"

    }
}