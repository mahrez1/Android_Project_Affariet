package tn.esprit.mylast.utils



import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.mylast.R
import com.bumptech.glide.Glide

import com.bumptech.glide.request.RequestOptions


import tn.esprit.mylast.models.Lot
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.internal.bind.TypeAdapters.URL
import java.net.URI
import java.net.URLDecoder


class LotAdapter (val TerrainList: MutableList<Lot>) : RecyclerView.Adapter<LotAdapter.TerrainViewHolder>(){
    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerrainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.terrain_single_item, parent, false)


        return TerrainViewHolder(view)

    }

    override fun onBindViewHolder(holder: TerrainViewHolder, position: Int) {
      //  holder.dis.setText(TerrainList.get(position).description)

         val name = TerrainList[position].localisation
       val role = TerrainList[position].description
       // val im = TerrainList[position].image.toString()
       // holder.img.text = im
        holder.dis.text = role
        holder.cat.text = name

        // holder.terr_Pic.setImageResource(TerrainList[position].picture)


//http://localhost:3000/
       val  url = "http://192.168.124.80:3000/"+TerrainList[position].image!!.replace("\\","/")
        println(url)
        //val afterDecode: String = URLDecoder.decode(url, "UTF-8")
        Glide.with(holder.itemView)
            .load(url)
            .into(holder.img)

      /*  TypeAdapters.URL(url).openStream().use { `is` ->
            val bitmap = BitmapFactory.decodeStream(`is`)

              URL(url).openStream().use { `is` ->
            val bitmap = BitmapFactory.decodeStream(`is`)

        }

        }*/




      /* val galleryPermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {


        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                101, galleryPermissions)
        }*/









           // .load(url)
           //    .into(holder.img)

        // Cours Description
        //holder.coursDesc.setText(coursPostsList.get(position).getCoursDesc())

       /* holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.apply {
                //putExtra(PICTURE, TerrainList[position].terrainPic)
               // putExtra(CATEGORIE, name)
               // putExtra(DESCRIPTION, role)
            }
            holder.itemView.context.startActivity(intent)

        }*/
        // Cours Description
        // Cours Title
        // Cours Title
      //  holder.coursTitle.setText(coursPostsList.get(position).getTile())














    }

    override fun getItemCount(): Int {
        return TerrainList.size
    }


class TerrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //val terr_Pic: ImageView
    val cat : TextView

   val view = itemView
    val dis: TextView
    val img: ImageView

    init {
     //   terr_Pic = itemView.findViewById<ImageView>(R.id.terrainPic)
       cat = itemView.findViewById<TextView>(R.id.localisation)
        dis = itemView.findViewById(R.id.description)
        img = itemView.findViewById(R.id.terrainPic)
    }
}}