package tn.esprit.mylast

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button



import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.legacy.app.ActivityCompat

class MainActivity2 : AppCompatActivity(), View.OnClickListener {
    private var image: Button? = null
    private var video: Button? = null
    private var pdf: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        image = findViewById(R.id.image) as Button?
        video = findViewById(R.id.video) as Button?
        pdf = findViewById(R.id.pdf) as Button?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED)
        {
            image!!.isEnabled = false
            video!!.isEnabled = false
            pdf!!.isEnabled = false
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0)
        } else {
            image!!.isEnabled = true
            video!!.isEnabled = true
            pdf!!.isEnabled = true
        }
        image!!.setOnClickListener(this)
        video!!.setOnClickListener(this)
        pdf!!.setOnClickListener(this)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray )

    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                image!!.isEnabled = true
                video!!.isEnabled = true
                pdf!!.isEnabled = true
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image -> {
                val intent = Intent(this, ImageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}