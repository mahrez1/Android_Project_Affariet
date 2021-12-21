package tn.esprit.mylast

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.mylast.utils.LotAdapter
import tn.esprit.mylast.models.Lot
import kotlinx.android.synthetic.main.activity_discover.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.utils.ApiInterface

class DiscoverActivity : AppCompatActivity() {
    lateinit var recylcerLots: RecyclerView
    lateinit var adapter: LotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
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
    fun goBack(){
        backButtonWraperDiscover.setOnClickListener {
            onBackPressed()
        }
    }
}