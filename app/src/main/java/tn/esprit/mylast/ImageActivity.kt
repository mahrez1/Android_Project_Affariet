package tn.esprit.mylast

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
//import android.support.v4.content.FileProvider
//import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider

import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
//import com.delaroystudios.uploadmedia.networking.ApiConfig
//import com.delaroystudios.uploadmedia.networking.AppConfig
//import com.delaroystudios.uploadmedia.networking.ServerResponse

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.Locale
import java.util.logging.Logger

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.mylast.models.Lot
import tn.esprit.mylast.utils.ApiInterface
import android.content.ContentUris
import android.content.SharedPreferences
import android.widget.EditText
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


/**
 * Created by delaroy on 5/5/18.
 */
const val PREF_N = "UPLOAD_PREF_AFFARIET"

class ImageActivity : AppCompatActivity() {
    lateinit  var Shared  : SharedPreferences

    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var upload: Button
    private lateinit var Buttonf : Button
    private lateinit var localisation_et : EditText
    private lateinit var description_et : EditText
    private lateinit var price_et : EditText
    private val mMediaUri: Uri? = null
    private var selectedImageUri: Uri? = null

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private val btnCapturePicture: Button? = null
    lateinit var storage: FirebaseStorage
    val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formater.format(now)
    private var mImageFileLocation = ""
    private lateinit var pDialog: ProgressDialog
    private var postPath: String? = null
    private val galleryPermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_layout)
        Buttonf = findViewById(R.id.createPost)
        localisation_et  = findViewById(R.id.titlePost)
        description_et  = findViewById(R.id.descPost)
        price_et = findViewById(R.id.pricePost)

        imageView = findViewById(R.id.preview) as ImageView
        pickImage = findViewById(R.id.pickImage) as Button
        upload = findViewById(R.id.upload) as Button

        pickImage.setOnClickListener{
            openGallery()
        }
       upload.setOnClickListener{
           uploadImage()
       }
        Buttonf.setOnClickListener{
            doUpload()

            val intent = Intent(this@ImageActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //initDialog()
    }//https://firebasestorage.googleapis.com/v0/b/my-last-fc686.appspot.com/o/uploads%2F++?alt=media
    private fun uploadImage()
    {
        if (selectedImageUri == null) {
            Toast.makeText(this@ImageActivity,"Please Select Picture", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading Image ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val storageReference = FirebaseStorage.getInstance().reference.child("uploads/$fileName")
            storageReference.putFile(selectedImageUri!!).
            addOnSuccessListener {
                imageView!!.setImageURI(selectedImageUri)
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Successfuly uploaded", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                if(progressDialog.isShowing)
                {
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Sorry", Toast.LENGTH_SHORT).show()

            }







        }

    }

    private fun doUpload()
    {
        val getResponse = ApiInterface.create()
        val call = getResponse.upload(localisation_et.text.toString().trim(),description_et.text.toString().trim() ,price_et.text.toString().trim() ,fileName)

        call.enqueue(object : Callback<Lot> {
            override fun onResponse(call: Call<Lot>, response: Response<Lot>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        //  hidepDialog()
                        // val serverResponse = response.body()
                        Toast.makeText(applicationContext, "upload gone ok !", Toast.LENGTH_SHORT).show()
                       /* Shared= getSharedPreferences(PREF_N, MODE_PRIVATE)
                        Log.i("file",fileName.toString())

                        val editor : SharedPreferences.Editor = Shared.edit()
                        editor.putString("pic" , fileName.toString())
                        editor.apply()*/

                    }
                } else {
                    Toast.makeText(applicationContext, "problem uploading image", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Lot>, t: Throwable) {
                //Log.v("Response gotten is", t.message)
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK)
        {
            selectedImageUri = data?.data!!
            imageView!!.setImageURI(selectedImageUri)
        }
    }
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()
                    val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0).toLong())

                   val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                   mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()


                    postPath = mediaPath
                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(imageView)
                    postPath = mImageFileLocation

                } else {
                    Glide.with(this).load(fileUri).into(imageView)
                    postPath = fileUri!!.path

                }

            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }
    }*/

   /* protected fun initDialog() {

        pDialog = ProgressDialog(this)
        pDialog.setMessage(getString(R.string.msg_loading))
        pDialog.setCancelable(true)
    }


    protected fun showpDialog() {

        if (!pDialog.isShowing) pDialog.show()
    }

    protected fun hidepDialog() {

        if (pDialog.isShowing) pDialog.dismiss()
    }*/


    /**
     * Launching camera app to capture image
     */
    private fun captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE

            // We give some instruction to the intent to save the image
            var photoFile: File? = null

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile()
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (e: IOException) {
                Logger.getAnonymousLogger().info("Exception error in generating the file")
                e.printStackTrace()
            }

            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            val outputUri = photoFile?.let {
                FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    it)
            }
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

            Logger.getAnonymousLogger().info("Calling the camera App by intent")

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }


    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        Logger.getAnonymousLogger().info("Generating the image - method started")

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        // Here we specify the environment location and the exact path where we want to save the so-created file
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app")
        Logger.getAnonymousLogger().info("Storage directory set")

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir()

        // Here we create the file using a prefix, a suffix and a directory
        val image = File(storageDirectory, imageFileName + ".jpg")
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set")

        mImageFileLocation = image.absolutePath
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri")
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */

    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * Creating file uri to store image/video
     */
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    // Uploading Image/Video


    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        private val TAG = ImageActivity::class.java.getSimpleName()

        private val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        private fun getOutputMediaFile(type: Int): File? {

            // External sdcard location
            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory")
                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }

            return mediaFile
        }
    }


}



