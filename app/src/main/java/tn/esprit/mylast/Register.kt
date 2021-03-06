package tn.esprit.mylast
import android.Manifest
import tn.esprit.mylast.R

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

import org.w3c.dom.Text
import android.app.Activity
import android.app.AlertDialog
import android.content.Context

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat

import android.os.Build
import android.content.DialogInterface
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.models.User
import tn.esprit.mylast.utils.ApiInterface
import tn.esprit.mylast.utils.LotAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "NAME"

private const val ARG_EMAIL = "EMAIL"
private const val ARG_PIC = "Pic"
private var uri: Uri? = null


/**
 * A simple [Fragment] subclass.
 * Use the [test.newInstance] factory method to
 * create an instance of this fragment.
 */
lateinit  var sharedPreff  : SharedPreferences

class Result : Fragment() {
    // TODO: Rename and change types of parameters
    private var name_string: String? = null

    private var mail_string: String? = null
    //   private var pic_string: String? = null
    lateinit var recylcerTerrainAdapter: LotAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name_string = it.getString(ARG_NAME)

            mail_string = it.getString(ARG_EMAIL)
            //  pic_string = it.getString(ARG_PIC)


            // val img= intent.getStringExtra("Image")
            // val fileUri = Uri.parse(img)
            // picture.setImageURI(fileUri)



        }

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.nav_header_main, container, false)
        // Inflate the layout for this fragment
        // val picture = v.findViewById<ImageView>(R.id.pic)
        /* var picture = v.findViewById<ImageView>(R.id.pic)
         val extras = getIntent().extras
         val path: Uri = extras?.get("Image") as Uri
        val p= picture.setImageURI(path) */

        val nameText = v.findViewById<TextView>(R.id.usernamep)
        val emailText = v.findViewById<TextView>(R.id.usermailp)
        var pic = v.findViewById<ImageView>(R.id.pic)
      /*  sharedPreff = requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        Log.i("imageaaaa","aaaaaaaaaaaa"+sharedPreff.getString("image",""))

        //if(sharedPreff!=null){ }
        //else{ pic.setImageResource(v.findViewById<TmageView>(R.id.pic))}
        //toString()!!.toUri())
        if (checkPermissionREAD_EXTERNAL_STORAGE(requireContext())) {
            pic.setImageURI(sharedPreff.getString("image","")!!.toUri())
        }*/






        pic.setOnClickListener { startActivity(Intent(context,ProfileActivity::class.java))}


        if (arguments != null) {
            name_string = requireArguments().getString(ARG_NAME)

            mail_string = requireArguments().getString(ARG_EMAIL)
            //  pic_string = requireArguments().getString(ARG_PIC)

        }
        nameText.text = "${name_string}"

        emailText.text = "${mail_string}"
        //= "${pic_string}"



        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  setContentView(R.layout.fragment_home)


        val apiInterface = ApiInterface.create()
        // var call = apiInterface.posts
        sharedPreff = requireActivity().getSharedPreferences(PREF_NAMEE, AppCompatActivity.MODE_PRIVATE)

        val sh=sharedPreff.getString("ID","")
        apiInterface.getOne(id =sh ).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) { }
            override fun onResponse(call : Call<User>, response : Response<User>)
            {
               val user = response.body()
               val picc = user?.picture
                val p="https://firebasestorage.googleapis.com/v0/b/my-last-fc686.appspot.com/o/uploads%2F$picc?alt=media"
                println(p)
                //val afterDecode: String = URLDecoder.decode(url, "UTF-8")
                Log.i("jooo","ggggggg"+picc)

                val u = p.toUri()
                Picasso.with(requireActivity()).load(u).into(pic)




            }



        })

    }



















    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    fun checkPermissionREAD_EXTERNAL_STORAGE(
        context: Context
    ): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        return if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showDialog(
                        "External storage", context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } else {
                    ActivityCompat
                        .requestPermissions(
                            (context as Activity?)!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }
    fun showDialog(
        msg: String, context: Context?,
        permission: String
    ) {
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("$msg permission is necessary")
        alertBuilder.setPositiveButton(android.R.string.yes,
            DialogInterface.OnClickListener { dialog, which ->
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!, arrayOf(permission),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
            })
        val alert: AlertDialog = alertBuilder.create()
        alert.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment result.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(
            param1: String?,
            param2: String?
            //  param3: String?


        ) = Result().apply {

            arguments = Bundle().apply {
                putString(ARG_NAME, param1)
                putString(ARG_EMAIL, param2)
                //  putString( ARG_PIC, param3)

            }
        }
    }


}