package tn.esprit.mylast.ui.gallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import com.bumptech.glide.Glide
import com.ramotion.circlemenu.CircleMenuView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import tn.esprit.mylast.BuildConfig
import tn.esprit.mylast.R
import tn.esprit.mylast.databinding.FragmentGalleryBinding
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.utils.ApiInterface
import java.io.*
import java.lang.Exception
import java.security.AccessController.checkPermission
import java.text.SimpleDateFormat
import java.util.*

val TAG = "ADDPOST_FRAGMENT"
val GALLERY_REQUEST_CODE = 1
val CAMERA_REQUEST_CODE = 2
val CAMERA_PERMISSION_CODE = 100
val STORAGE_PERMISSION_CODE = 101
var disposable = CompositeDisposable()
var Categorydisposable = CompositeDisposable()
class GalleryFragment : Fragment(), HandlePathOzListener.SingleUri {


    private var cameraFilePath: String? = null
    private val userApi: ApiInterface? = null
    var lisLot: List<Lot>? = null
    var imgPost: ImageView? = null
    var titlePost: EditText? = null
    var pricePost: EditText? = null
    var descPost: EditText? = null
    var addPost: Button? = null
    var containerpost: LinearLayout? = null
    var mBitmap: Bitmap? = null
    var errorNotice: TextView? = null
    private val handlePathOz: HandlePathOz? = null
    private var selectedImagePath: String? = null

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var b : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {



        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textGallery




        return root


    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgPost = view.findViewById<ImageView>(R.id.imgPost)
        titlePost = view.findViewById<EditText>(R.id.titlePost)
        pricePost = view.findViewById<EditText>(R.id.pricePost)
        descPost = view.findViewById<EditText>(R.id.descPost)
        addPost = view.findViewById<Button>(R.id.createPost)
        containerpost = view.findViewById<LinearLayout>(R.id.postContainer)
        errorNotice = view.findViewById<TextView>(R.id.postErrorNotice)
        b = view.findViewById(R.id.createPost)
        val handlePathOz = HandlePathOz(requireContext(), this)
        val circleMenuView = view.findViewById<View>(R.id.circle_menu)
        // submit post
        with(addPost) {
            circleMenuView.setEventListener(object : CircleMenuView.EventListener() {
                override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                    Log.d("D", "onMenuOpenAnimationStart")
                }

                override fun onMenuOpenAnimationEnd(view: CircleMenuView) {
                    Log.d("D", "onMenuOpenAnimationEnd")
                }

                override fun onMenuCloseAnimationStart(view: CircleMenuView) {
                    Log.d("D", "onMenuCloseAnimationStart")
                }

                override fun onMenuCloseAnimationEnd(view: CircleMenuView) {
                    Log.d("D", "onMenuCloseAnimationEnd")
                }

                override fun onButtonClickAnimationStart(view: CircleMenuView, index: Int) {
                    Log.d("D", "onButtonClickAnimationStart| index: $index")
                }

                override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                    Log.d("D", "onButtonClickAnimationEnd| index: $index")
                    if (index == 0) {
                        checkPermission(0,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            tn.esprit.mylast.ui.gallery.STORAGE_PERMISSION_CODE)
                        with(containerpost) { this?.setVisibility(View.VISIBLE) }
                        circleMenuView.visibility = View.GONE

                    } else if (index == 1) {
                        checkPermission(1,
                            Manifest.permission.CAMERA,
                            tn.esprit.mylast.ui.gallery.CAMERA_PERMISSION_CODE)
                        with(containerpost) { this?.setVisibility(View.VISIBLE) }
                        circleMenuView.visibility = View.GONE

                    }
                }
            })


            // submit post

            // submit post
            b.setOnClickListener(View.OnClickListener {
                if ((titlePost.toString().isNotEmpty()) and (pricePost.toString().isNotEmpty()) and (descPost.toString().isNotEmpty())) {
                    SubmitPost()
                } else {
                    Toast.makeText(requireContext(), "Information is saved", Toast.LENGTH_SHORT).show()

                }
            })
        }




    }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = view.findViewById(R.id.createPost)

        b.setOnClickListener(View.OnClickListener {
            if ((titlePost.toString().isNotEmpty()) and (pricePost.toString().isNotEmpty()) and (descPost.toString().isNotEmpty())) {
                SubmitPost()
            } else {
                Toast.makeText(requireContext(), "Information is saved", Toast.LENGTH_SHORT).show()

            }
        })


    }



    private fun pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_PICK)
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.type = "image/*"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        // Launching the Intent
        startActivityForResult(intent,
            tn.esprit.mylast.ui.gallery.GALLERY_REQUEST_CODE)
    }


     override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result code is RESULT_OK only if the user selects an Image
        val selectedImage: Uri?
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                tn.esprit.mylast.ui.gallery.GALLERY_REQUEST_CODE -> {
                    //data.getData return the content URI for the selected Image
                    selectedImage = data?.data
                    if (selectedImage != null) {
                        handlePathOz!!.getRealPath(selectedImage)
                        Glide.with(requireContext())
                            .load(selectedImage)
                            .into(imgPost!!)
                    }
                }
                tn.esprit.mylast.ui.gallery.CAMERA_REQUEST_CODE -> imgPost!!.setImageURI(
                    Uri.parse(cameraFilePath))
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM), "Camera")
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for using again
        cameraFilePath = "file://" + image.absolutePath
        return image
    }


    private fun captureFromCamera() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID.toString() + ".provider",
                    createImageFile()!!))
            startActivityForResult(intent,
                tn.esprit.mylast.ui.gallery.CAMERA_REQUEST_CODE)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }


    fun checkPermission(TAG: Int, permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission)
            == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),
                requestCode)
        } else {
            Toast.makeText(context,
                "Permission already granted",
                Toast.LENGTH_SHORT)
                .show()
            if (TAG == 0) {
                pickFromGallery()
            } else if (TAG == 1) {
                captureFromCamera()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super
            .onRequestPermissionsResult(requestCode,
                permissions,
                grantResults)
        if (requestCode == tn.esprit.mylast.ui.gallery.CAMERA_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context,
                    "Camera Permission Granted",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context,
                    "Camera Permission Denied",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == tn.esprit.mylast.ui.gallery.STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(context,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }



    fun SubmitPost() {
        if (mBitmap != null) {
            val filesDir = requireContext().filesDir
            val file = File(filesDir, "image" + ".png")
            val bos = ByteArrayOutputStream()
            mBitmap!!.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val title = titlePost!!.text.toString()
            val desc = descPost!!.text.toString()
            val price = pricePost!!.text.toString().toInt()
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
            val name = RequestBody.create(MediaType.parse("text/plain"), "image")
            val Title = RequestBody.create(MediaType.parse("multipart/form-data"), title)
            val Desc = RequestBody.create(MediaType.parse("multipart/form-data"), desc)
            val Price = RequestBody.create(MediaType.parse("multipart/form-data"), price.toString())

            userApi!!.addPost(Title.toString(), Desc.toString(), Price.toString(), body ,name)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.rxjava3.core.Observer<ResponseBody?> {
                    override fun onSubscribe(d: @NonNull Disposable?) {
                        Log.d(tn.esprit.mylast.ui.gallery.TAG,
                            "onSubscribe: created")
                        disposable.add(d)
                    }

                    override fun onNext(cat: @NonNull ResponseBody?) {
                        Log.d(tn.esprit.mylast.ui.gallery.TAG,
                            "onNext: $cat")
                    }

                    override fun onError(e: @NonNull Throwable?) {
                        Log.d(tn.esprit.mylast.ui.gallery.TAG, "onError: ", e)
                    }

                    override fun onComplete() {
                        Log.d(tn.esprit.mylast.ui.gallery.TAG,
                            "onComplete: created")
                        disposable.clear()
                    }
                })
        } else {
            Toast.makeText(context, "Selected image is empty, Request failed!", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        disposable.clear()
        Categorydisposable.clear()
    }

    override fun onRequestHandlePathOz(pathOz: PathOz, throwable: Throwable?) {
        selectedImagePath = pathOz.path
        Log.d(tn.esprit.mylast.ui.gallery.TAG,
            "onRequestHandlePathOz SELECTEDIMAGE_PATH : $selectedImagePath")
        if (selectedImagePath.toString().isNotEmpty()) {
            try {
                val file = File(selectedImagePath)
                mBitmap = BitmapFactory.decodeFile(file.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}

private fun View.setEventListener(eventListener: CircleMenuView.EventListener) {

}
