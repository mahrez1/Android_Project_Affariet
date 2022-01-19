package tn.esprit.mylast.ui.slideshow

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_discover.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.DiscoverActivity
import tn.esprit.mylast.PREF_NAME
import tn.esprit.mylast.R
import tn.esprit.mylast.databinding.FragmentSlideshowBinding
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.utils.ApiInterface
import tn.esprit.mylast.utils.LotAdapter

class SlideshowFragment : Fragment() {
    lateinit var recylcerLots: RecyclerView
    lateinit var adapter: LotAdapter
    lateinit  var sharedPree  : SharedPreferences

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // setContentView(R.layout.activity_discover)
        var userList: MutableList<Lot> = ArrayList()
        recylcerLots = lotsRecyclerView
        goBack()
        val apiInterface = ApiInterface.create()
        simpleSearchView.setBackgroundResource(R.drawable.btndark)
        simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                apiInterface.search(ApiInterface.SearchBody(newText)).enqueue(
                    object : Callback<ApiInterface.SearchResponse> {
                        override fun onResponse(
                            call: Call<ApiInterface.SearchResponse>,
                            response: Response<ApiInterface.SearchResponse>
                        ) {
                            userList.clear()

                            //     val preferences: SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

                            //    val userConnected =preferences.getString("id", "").toString()

                            if(userList.size==0){
                                noresulttext.visibility = View.VISIBLE

                            }
                            Log.i("recyler","aaaaaaaaaaaaaaaaa"+response.code()+response.body().toString())
                            if (response.code() == 200) {
                                recylcerLots.visibility = View.VISIBLE

                                response.body()!!.users.forEach {

                                    userList.add(it)
                                    Log.i("aa", userList.size.toString())
                                }
                                //  val filename2 = postlist[i].image
                                sharedPree = requireActivity().getSharedPreferences(PREF_NAME,
                                    AppCompatActivity.MODE_PRIVATE)

                                val filename2 = sharedPree.getString("pic", "").toString()
                                val path = "https://firebasestorage.googleapis.com/v0/b/my-last-fc686.appspot.com/o/uploads%2F+$filename2+?alt=media"

                                adapter = LotAdapter(userList)
                                //  Log.i("img", ApiInterface.BASE_URL + preferences.getString("avatar", ""))
                                recylcerLots.adapter = adapter
                                recylcerLots.setLayoutManager( LinearLayoutManager(DiscoverActivity()))
                                noresulttext.visibility = View.GONE
                            } else {
                                recylcerLots.visibility = View.GONE
                                noresulttext.visibility = View.VISIBLE
                                userList.clear()

                            }
                        }

                        override fun onFailure(
                            call: Call<ApiInterface.SearchResponse>,
                            t: Throwable
                        ) {
                            recylcerLots.visibility = View.GONE
                            noresulttext.visibility = View.VISIBLE
                            userList.clear()

                        }

                    }
                )
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })



    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun goBack(){
        backButtonWraperDiscover.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}