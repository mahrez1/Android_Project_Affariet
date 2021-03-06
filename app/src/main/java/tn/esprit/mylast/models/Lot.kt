package tn.esprit.mylast.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*data class Lot (
    var id: Int,
    var localisation: String?=null,
    var description: String?=null,
    var picture: String

)*/
const val picture = "picture"
const val localisation = "localisation"
const val description = "description"
const val contact = "contact"


class Lot {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("localisation")
    @Expose
    var localisation: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("contact")
    @Expose
    var contact: String? = null
    @SerializedName("picture")
    @Expose
    var image: String? = null

    constructor(
        id: Int,
        localisation : String?,
        description: String?,
        price: String?,
        contact: String?,
        image: String?,
    ) {
        this.id = id
        this.localisation = localisation
        this.description = description
        this.price = price
        this.contact = contact
        this.image = image
    }

    constructor() {}
}
