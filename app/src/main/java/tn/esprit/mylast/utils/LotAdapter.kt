package tn.esprit.mylast.utils



import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.mylast.R
import com.bumptech.glide.Glide


import tn.esprit.mylast.models.Lot

import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.internal.bind.TypeAdapters.URL
import tn.esprit.mylast.PREF_N

import java.net.URI
import java.net.URLDecoder



class LotAdapter (val TerrainList: MutableList<Lot>) : RecyclerView.Adapter<LotAdapter.TerrainViewHolder>(){

  //  lateinit  var sharedPre  : SharedPreferences

    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerrainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.terrain_single_item, parent, false)



        return TerrainViewHolder(view)

    }



    override fun onBindViewHolder(holder: TerrainViewHolder, position: Int) {
      //  holder.dis.setText(TerrainList.get(position).description)

        //val path =
            //"https://firebasestorage.googleapis.com/v0/b/mini-projet-2e934.appspot.com/o/images%2F$filename2?alt=media"


         val name = TerrainList[position].localisation
       val role = TerrainList[position].description
         val filename2 = TerrainList[position].image

        // val im = TerrainList[position].image.toString()
       // holder.img.text = im
        holder.dis.text = role
        holder.cat.text = name


        // holder.terr_Pic.setImageResource(TerrainList[position].picture)


//http://localhost:3000/
       // var sharedPre : SharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE);


        val path = "https://firebasestorage.googleapis.com/v0/b/my-last-fc686.appspot.com/o/uploads%2F+$filename2+?alt=media"

        println(filename2)
        //val afterDecode: String = URLDecoder.decode(url, "UTF-8")
        Glide.with(holder.itemView)
            .load(filename2)
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