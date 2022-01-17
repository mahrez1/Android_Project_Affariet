package tn.esprit.mylast.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.terrain_single_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.mylast.PREF_N
import tn.esprit.mylast.R
import tn.esprit.mylast.data.Terrain
import tn.esprit.mylast.databinding.FragmentHomeBinding
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.models.User
import tn.esprit.mylast.sharedPreff
import tn.esprit.mylast.utils.ApiInterface
import tn.esprit.mylast.utils.LotAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var recylcerTerrainAdapter: LotAdapter
    lateinit  var sharedPre  : SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,


        ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setContentView(R.layout.fragment_home)


        val apiInterface = ApiInterface.create()
       // var call = apiInterface.posts

        apiInterface.get()?.enqueue(object : Callback <MutableList<Lot?>?> {
                override fun onFailure(call: Call<MutableList<Lot?>?>, t: Throwable) { }
                override fun onResponse( call : Call<MutableList<Lot?>?> , response : Response<MutableList<Lot?>?>)
                {
                    Log.i("api",response.body()?.size.toString())
                  var postlist : MutableList<Lot> = (response.body() as? MutableList<Lot>)!!
                   var post = arrayOfNulls<String>(postlist.size)
                   for(i in postlist.indices)
                        post[i] = postlist[i].description

                    // Log.i("koko","ggggggg"+description)

                    //  val array= ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line,post)
                 //   array.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                  //  requireActivity().getSharedPreferences(PREF_N, AppCompatActivity.MODE_PRIVATE)
                    //  val filename2 = postlist[i].image


                     recylcerTerrainAdapter = LotAdapter(postlist)
                    recyclerterrain.adapter = recylcerTerrainAdapter
                    recyclerterrain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)



                }



            })
     /*   var TerrainList : MutableList<Terrain> = ArrayList()

        TerrainList.add(Terrain(terrainPic = R.drawable.download, categorie = "industriel", description = "Nabeul , Centre-ville , 1100m²  " ))
        TerrainList.add(Terrain(terrainPic = R.drawable.download11, categorie = "Résidentiel", description = "touzeur , Centre-ville , 340m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.images, categorie = "agriculteur", description = "Nabeul , korba , 1040m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.images1, categorie = "Résidentiel", description = "Nabeul , Centre-ville , 1000m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.terrain_a_louer_1850131614697445752, categorie = "Résidentiel", description = "tunis , ariena soghra , 221m²" ))

        recylcerTerrainAdapter = LotAdapter(TerrainList)

        recyclerterrain.adapter = recylcerTerrainAdapter

        recyclerterrain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)*/

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}