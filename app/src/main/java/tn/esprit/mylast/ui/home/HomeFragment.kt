package tn.esprit.mylast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_home.*
import tn.esprit.mylast.R
import tn.esprit.mylast.data.Terrain
import tn.esprit.mylast.databinding.FragmentHomeBinding
import tn.esprit.mylast.utils.LotAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var recylcerTerrainAdapter: LotAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var TerrainList : MutableList<Terrain> = ArrayList()

        TerrainList.add(Terrain(terrainPic = R.drawable.download, categorie = "industriel", description = "Nabeul , Centre-ville , 1100m²  " ))
        TerrainList.add(Terrain(terrainPic = R.drawable.download11, categorie = "Résidentiel", description = "touzeur , Centre-ville , 340m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.images, categorie = "agriculteur", description = "Nabeul , korba , 1040m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.images1, categorie = "Résidentiel", description = "Nabeul , Centre-ville , 1000m²" ))
        TerrainList.add(Terrain(terrainPic = R.drawable.terrain_a_louer_1850131614697445752, categorie = "Résidentiel", description = "tunis , ariena soghra , 221m²" ))

        recylcerTerrainAdapter = LotAdapter(TerrainList)

        recyclerterrain.adapter = recylcerTerrainAdapter

        recyclerterrain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}