package tn.esprit.mylast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val buttonBack = findViewById<FrameLayout>(R.id.backBytton)


        buttonBack.setOnClickListener {
            onBackPressed()
        }


        ManageAccount.setOnClickListener {
            val intent = Intent(this,ManageAccoount::class.java)
            startActivity(intent)}


    }
}