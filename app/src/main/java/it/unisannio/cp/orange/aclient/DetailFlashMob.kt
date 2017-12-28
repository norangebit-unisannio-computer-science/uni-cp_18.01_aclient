package it.unisannio.cp.orange.aclient

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import it.unisannio.cp.orange.aclient.rest.Path
import kotlinx.android.synthetic.main.activity_detail_flash_mob.*
import kotlinx.android.synthetic.main.content_detail_flash_mob.*

class DetailFlashMob : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_flash_mob)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val fm = ListInstance.get(intent.getIntExtra(Key.POS, 0))
        val bundle = intent.getBundleExtra(Key.COVER)

        title = fm.name
        description.text = fm.description
        Picasso.with(this).load("${Path.ip}/${fm.name}/${Path.COVER}").into(cover)
    }

}
