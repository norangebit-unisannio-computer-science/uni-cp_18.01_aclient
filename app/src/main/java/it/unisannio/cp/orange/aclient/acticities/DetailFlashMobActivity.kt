package it.unisannio.cp.orange.aclient.acticities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import it.unisannio.cp.orange.aclient.adapters.Key
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.adapters.PicAdapter
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.network.rest.GetPhotos
import it.unisannio.cp.orange.aclient.network.rest.Path
import it.unisannio.cp.orange.aclient.util.Util
import kotlinx.android.synthetic.main.activity_detail_flash_mob.*
import kotlinx.android.synthetic.main.content_detail_flash_mob.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import it.unisannio.cp.orange.aclient.network.rest.PostPhoto
import kotlin.collections.ArrayList


class DetailFlashMobActivity : AppCompatActivity() {

    var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_flash_mob)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            if (Util.checkPermission(applicationContext, android.Manifest.permission.CAMERA))
                takeAndUploadPhoto()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION)
        }

        val fm = ListInstance.get(intent.getIntExtra(Key.POS, 0))

        val list = ArrayList<String>()
        val adapter = PicAdapter(list)

        title = fm.name
        adapter.name = fm.name
        Glide.with(this).load("${Path.ip}/${fm.name}/${Path.COVER}").into(pic)


        val llm = LinearLayoutManager(applicationContext)
        picList.layoutManager = llm
        picList.adapter = adapter

        GetPhotos(list, adapter).execute("${Path.ip}/${fm.name}/photos")

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CAMERA_PERMISSION -> {
                if(grantResults.none { it == PackageManager.PERMISSION_DENIED })
                    takeAndUploadPhoto()
                else
                    Toast.makeText(this, getString(R.string.no_permission), Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       when(requestCode){
           0 -> {
               if(resultCode == Activity.RESULT_OK)
                   PostPhoto().execute(imagePath, title.toString())
           }
           else -> super.onActivityResult(requestCode, resultCode, data)
       }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMG_" + timeStamp
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, "", storageDirectory)
        imagePath = image.absolutePath
        return image
    }

    fun takeAndUploadPhoto(){
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val outputUri = FileProvider.getUriForFile(this, AUTHORITY, photoFile)
        camera.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        startActivityForResult(camera, 0)
    }

    companion object {
        val AUTHORITY = "it.unisannio.cp.orange.aclient.fileProvider"
        val CAMERA_PERMISSION = 1000
    }

}
