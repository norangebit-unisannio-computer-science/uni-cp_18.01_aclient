package it.unisannio.cp.orange.aclient.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import commons.FlashMob
import it.unisannio.cp.orange.aclient.adapters.FlashMobAdapter
import it.unisannio.cp.orange.aclient.util.change


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
        sp?.change { putString(fm.name, Gson().toJson(fm, FlashMob::class.java)) }
    }

    fun get(pos: Int) = list[pos]

    fun remove(name: String){
        list.removeAll(list.filter { it.name == name })
        adapter.notifyDataSetChanged()
        sp?.change { remove(name) }
    }

    fun setState(name: String, state: Boolean) = sp?.change { putBoolean(name, state) }

    fun setContext(context: Context){
        sp = context.getSharedPreferences("list", Context.MODE_PRIVATE)
    }

    fun load(){
        sp?.all?.keys?.forEach { ListInstance.list.add(Gson().fromJson(ListInstance.sp?.getString(it, "null"), FlashMob::class.java)) }
        adapter.notifyDataSetChanged()
    }
}