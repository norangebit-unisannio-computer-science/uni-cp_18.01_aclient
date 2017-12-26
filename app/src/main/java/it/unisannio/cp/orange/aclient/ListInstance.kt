package it.unisannio.cp.orange.aclient

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import commons.FlashMob


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 26/12/17
 *  Last Modified: $file.lastModified
 */
 
 
object ListInstance{
    private val list = ArrayList<FlashMob>()
    val adapter = FlashMobAdapter(list)
    var sp: SharedPreferences? = null

    fun add(fm: FlashMob){
        list.add(fm)
        adapter.notifyDataSetChanged()
        sp?.change { putBoolean(fm.name, false) }
    }

    fun remove(name: String){
        list.removeAll(list.filter { it.name == name })
        adapter.notifyDataSetChanged()
        sp?.change { remove(name) }
    }

    fun setState(name: String, state: Boolean) = sp?.change { putBoolean(name, state) }

    fun setContext(context: Context){
        sp = context.getSharedPreferences("list", Context.MODE_PRIVATE)
    }
    fun SharedPreferences.change(func: SharedPreferences.Editor.()->SharedPreferences.Editor) = this.edit().func().apply()
}