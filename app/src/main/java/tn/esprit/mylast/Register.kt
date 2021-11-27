package tn.esprit.mylast
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "Name"

private const val ARG_EMAIL = "Email"
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
        sharedPreff = requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        Log.i("imageaaaa","aaaaaaaaaaaa"+sharedPreff.getString("image",""))

        //if(sharedPreff!=null){ }
        //else{ pic.setImageResource(v.findViewById<TmageView>(R.id.pic))}
        //toString()!!.toUri())

        pic.setImageURI(sharedPreff.getString("image","")!!.toUri())




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