package it.unisannio.cp.orange.aclient.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import it.unisannio.cp.orange.aclient.model.ListInstance
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.network.rest.GetList
import it.unisannio.cp.orange.aclient.network.rest.Path
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.change
import it.unisannio.cp.orange.aclient.util.getSettings

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var settings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        settings = getSettings(R.xml.pref_general)

        if(settings?.getBoolean(Util.KEY_FIRST_LAUNCH, true) ?: true)
            startActivityForResult(Intent(this, IntroActivity::class.java), Util.CODE_INTRO)

        ListInstance.setContext(this)
        GetList().execute("${Path.ip}/list")

        val llm = LinearLayoutManager(applicationContext)
        listRV.layoutManager = llm
        listRV.adapter = ListInstance.adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> openSettings()
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openSettings(): Boolean{
        val settings = Intent(this, SettingsActivity::class.java)
        settings.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true)
        settings.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment::class.java.name)
        startActivity(settings)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            Util.CODE_INTRO -> settings?.change { putBoolean(Util.KEY_FIRST_LAUNCH, false) }
        }
    }

}
