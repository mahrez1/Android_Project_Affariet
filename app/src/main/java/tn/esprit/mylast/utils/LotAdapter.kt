package tn.esprit.mylast.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.mylast.R
import tn.esprit.mylast.data.CATEGORIE
import tn.esprit.mylast.data.DESCRIPTION
import tn.esprit.mylast.data.PICTURE
import tn.esprit.mylast.data.Terrain



class LotAdapter (val TerrainList: MutableList<Terrain>) : RecyclerView.Adapter<LotAdapter.TerrainViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerrainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.terrain_single_item, parent, false)

        return TerrainViewHolder(view)
    }

    override fun onBindViewHolder(holder: TerrainViewHolder, position: Int) {
        val name = TerrainList[position].categorie
        val role = TerrainList[position].description

        holder.terr_Pic.setImageResource(TerrainList[position].terrainPic)
        holder.cat.text = name
        holder.dis.text = role

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.apply {
                putExtra(PICTURE, TerrainList[position].terrainPic)
                putExtra(CATEGORIE, name)
                putExtra(DESCRIPTION, role)
            }
            holder.itemView.context.startActivity(intent)

        }

    }


    override fun getItemCount() = TerrainList.size


class TerrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val terr_Pic: ImageView
    val cat: TextView
    val dis: TextView = itemView.findViewById<TextView>(R.id.description)

    init {
        terr_Pic = itemView.findViewById<ImageView>(R.id.terrainPic)
        cat = itemView.findViewById<TextView>(R.id.categorie)
    }
}}