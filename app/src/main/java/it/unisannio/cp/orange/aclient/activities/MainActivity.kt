package it.unisannio.cp.orange.aclient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.network.rest.GetList
import it.unisannio.cp.orange.aclient.network.rest.Path

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val sp = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

        if(sp.getBoolean("first", true))
            startActivityForResult(Intent(this, IntroActivity::class.java), INTRO_CODE)

        ListInstance.setContext(this)
        GetList().execute("${Path.ip}/list")

        val llm = LinearLayoutManager(applicationContext)
        listRV.layoutManager = llm
        listRV.adapter = ListInstance.adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            INTRO_CODE -> Log.d("INTRO", "in")
        }
    }

    companion object {
        val INTRO_CODE = 1000
    }
}
