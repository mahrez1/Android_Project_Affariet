package tn.esprit.mylast

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.utils.ApiInterface
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "ADDPOST_FRAGMENT"
const val GALLERY_REQUEST_CODE = 1
const val CAMERA_REQUEST_CODE = 2
const val CAMERA_PERMISSION_CODE = 100
const val STORAGE_PERMISSION_CODE = 101
var disposable = CompositeDisposable()
var Categorydisposable = CompositeDisposable()

class TestActivity : AppCompatActivity() , HandlePathOzListener.SingleUri {


    private var cameraFilePath: String? = null
    private val userApi: ApiInterface? = null
    var lisLot: List<Lot>? = null
    var imgPost: ImageView? = null
    var titlePost: EditText? = null
    var pricePost: EditText? = null
    var descPost: EditText? = null
    var addPostt: Button? = null
    var containerpost: LinearLayout? = null
    var mBitmap: Bitmap? = null
    private val handlePathOz: HandlePathOz? = null
    private var selectedImagePath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        addPostt = findViewById(R.id.button)
        pricePost = findViewById(R.id.pricePost)
        titlePost = findViewById(R.id.titlePost)
        descPost = findViewById(R.id.descPost)
        imgPost = findViewById(R.id.imgPost)



        addPostt = findViewById(R.id.createPostt)

        addPostt?.setOnClickListener(View.OnClickListener {
            if ((titlePost.toString().isNotEmpty()) and (pricePost.toString().isNotEmpty()) and (descPost.toString().isNotEmpty())) {
                SubmitPost()
            } else {
                Toast.makeText(this, "Information is saved", Toast.LENGTH_SHORT).show()

            }
        })

        imgPost?.setOnClickListener {

            //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
           // intent.type = "image/*"
           //onActivityResult(1,101, intent)
            val intent = Intent(Intent.ACTION_PICK)
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.type = "image/*"
            startActivityForResult(intent, 1)
            onActivityResult(1,2,intent)



            }



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
            GALLERY_REQUEST_CODE )
        onActivityResult(1,2,intent)


    }


    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result code is RESULT_OK only if the user selects an Image
        val selectedImage: Uri?
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    //data.getData return the content URI for the selected Image
                    selectedImage = data?.data

                    Log.i("m","aaaaaaaaaaaaaaaaa"+selectedImage.toString())

                    if (selectedImage != null) {
                        handlePathOz!!.getRealPath(selectedImage)
                        Glide.with(this)
                            .load(selectedImage)
                            .into(imgPost!!)
                    }
                }
                CAMERA_REQUEST_CODE -> imgPost!!.setImageURI(
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
                FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID.toString() + ".provider",
                    createImageFile()!!))
            startActivityForResult(intent,
                CAMERA_REQUEST_CODE)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }


    fun checkPermission(TAG: Int, permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission)
            == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission),
                requestCode)
        } else {
            Toast.makeText(this,
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
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this,
                    "Camera Permission Granted",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this,
                    "Camera Permission Denied",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }



    fun SubmitPost() {
        if (mBitmap != null) {
            val filesDir = this.filesDir
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
                        Log.d(TAG,
                            "onSubscribe: created")
                        disposable.add(d)
                    }

                    override fun onNext(cat: @NonNull ResponseBody?) {
                        Log.d(TAG,
                            "onNext: $cat")
                    }

                    override fun onError(e: @NonNull Throwable?) {
                        Log.d(TAG, "onError: ", e)
                    }

                    override fun onComplete() {
                        Log.d(TAG,
                            "onComplete: created")
                        disposable.clear()
                    }
                })
        } else {
            Toast.makeText(this, "Selected image is empty, Request failed!", Toast.LENGTH_SHORT)
                .show()
        }
    }


     override fun onRequestHandlePathOz(pathOz: PathOz, throwable: Throwable?) {
        selectedImagePath = pathOz.path
        Log.d(TAG,
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