package it.unisannio.cp.orange.aclient.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.bumptech.glide.Glide
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.adapters.PicAdapter
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.model.RequestPermission
import it.unisannio.cp.orange.aclient.network.rest.GetPhotos
import it.unisannio.cp.orange.aclient.network.rest.Path
import it.unisannio.cp.orange.aclient.network.rest.PostPhoto
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.checkPermission
import it.unisannio.cp.orange.aclient.util.toast
import kotlinx.android.synthetic.main.activity_detail_flash_mob.*
import kotlinx.android.synthetic.main.content_detail_flash_mob.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailFlashMobActivity : AppCompatActivity(), RequestPermission {

    var imagePath = ""
    var sp: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_flash_mob)
        setSupportActionBar(toolbar)

        sp = getSharedPreferences(Util.SP_SETTINGS, Context.MODE_PRIVATE)

        fab.setOnClickListener {
            if (checkPermission(android.Manifest.permission.CAMERA))
                takeAndUploadPhoto()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), Util.CODE_CAMERA_PERMISSION)
        }

        val fm = ListInstance.get(intent.getIntExtra(Util.KEY_POS, 0))

        val list = ArrayList<String>()
        val adapter = PicAdapter(list, this)

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
            Util.CODE_CAMERA_PERMISSION -> {
                if(grantResults.none { it == PackageManager.PERMISSION_DENIED })
                    takeAndUploadPhoto()
                else
                    toast(R.string.no_permission)
            }
            Util.CODE_SD_PERMISSION -> {
                if(grantResults.none { it == PackageManager.PERMISSION_DENIED })
                    toast(R.string.permission)
                else
                    toast(R.string.no_permission)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       when(requestCode){
           Util.CODE_UPLOAD -> {
               if(resultCode == Activity.RESULT_OK)
                   PostPhoto().execute(sp?.getString(Util.KEY_USER, null),
                           sp?.getString(Util.KEY_PASSWORD, null), imagePath, title.toString())
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

    fun takeAndUploadPhoto() {
        if (sp?.getBoolean(Util.KEY_LOGIN, false) ?: false) {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val outputUri = FileProvider.getUriForFile(this, Util.AUTHORITY, photoFile)
            camera.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            startActivityForResult(camera, Util.CODE_UPLOAD)
        } else{
            Snackbar.make(findViewById(R.id.detail), R.string.upload_error, Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun requestPermission(vararg permission: String) = ActivityCompat.requestPermissions(this, permission, Util.CODE_SD_PERMISSION)

}
