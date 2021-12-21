package tn.esprit.mylast.data

import androidx.annotation.DrawableRes

const val PICTURE = "PICTURE"
const val CATEGORIE = "CATEGORIE"
const val DESCRIPTION = "DESCRIPTION"

data class Terrain (
       val terrainPic: String,
       val categorie: String,
       val description: String


)