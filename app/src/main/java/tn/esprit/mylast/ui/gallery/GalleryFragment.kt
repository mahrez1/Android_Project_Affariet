package tn.esprit.mylast.ui.gallery
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.legacy.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tn.esprit.mylast.ImageActivity
import tn.esprit.mylast.LoginActivity
import tn.esprit.mylast.R
import tn.esprit.mylast.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment()  {
    private var image: Button? = null


    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  requireActivity().setContentView(R.layout.fragment_gallery)

        //image = findViewById(R.id.image)
        image = view.findViewById(R.id.image)


        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED)
        {
            image!!.isEnabled = false

            ActivityCompat.requestPermissions((context as Activity?)!!,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0)
        } else {
            image!!.isEnabled = true

        }
       // image!!.setOnClickListener(this)
            image!!.setOnClickListener {
                startActivity(Intent(requireContext(), ImageActivity::class.java))
            }




    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray )

    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                image!!.isEnabled = true

            }
        }
    }

   /* override fun onClick(view: View) {
        when (view.id) {
            R.id.image -> {
                val intent = Intent(requireContext(), ImageActivity::class.java)
                startActivity(intent)
            }
        }
    }*/




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}