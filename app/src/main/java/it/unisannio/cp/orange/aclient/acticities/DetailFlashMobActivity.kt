package it.unisannio.cp.orange.aclient.acticities

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
import kotlinx.android.synthetic.main.activity_detail_flash_mob.*
import kotlinx.android.synthetic.main.content_detail_flash_mob.*
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import it.unisannio.cp.orange.aclient.network.rest.PostPhoto


class DetailFlashMobActivity : AppCompatActivity() {

    var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_flash_mob)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val outputUri = FileProvider.getUriForFile(this, "it.unisannio.cp.orange.aclient.fileProvider",
                    photoFile)
            camera.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            startActivityForResult(camera, 0)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       when(requestCode){
           0 -> PostPhoto().execute(imagePath, title.toString())
           else -> super.onActivityResult(requestCode, resultCode, data)
       }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMG_" + timeStamp
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, "", storageDirectory)
        imagePath = image.absolutePath
        return image
    }


}
