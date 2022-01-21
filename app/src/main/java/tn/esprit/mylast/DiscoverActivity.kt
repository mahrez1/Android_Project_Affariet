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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

}

}