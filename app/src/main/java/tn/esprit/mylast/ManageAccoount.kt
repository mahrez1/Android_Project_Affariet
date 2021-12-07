package tn.esprit.mylast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_manage_accoount.*


class ManageAccoount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_accoount)

        var amine : Boolean = true
        var youssef : Boolean = true

        upd_photo.setOnClickListener {

            if (amine == true) {
                update.visibility =View.VISIBLE
                amine = false

            }
             else {
                update.visibility =View.GONE
                amine = true

            }


        }

        Upd_info.setOnClickListener {

            if (youssef == true) {
                info.visibility =View.VISIBLE
                youssef = false

            }
            else {
                info.visibility =View.GONE
                youssef = true

            }


        }

        backBytton.setOnClickListener {
            onBackPressed()
        }

    }
}