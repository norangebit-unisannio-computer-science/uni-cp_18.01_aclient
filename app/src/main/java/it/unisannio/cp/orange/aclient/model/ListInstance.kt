package it.unisannio.cp.orange.aclient.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import commons.FlashMob
import it.unisannio.cp.orange.aclient.adapters.FlashMobAdapter
import it.unisannio.cp.orange.aclient.util.change
import java.util.*


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 26/12/17
 *  Last Modified: $file.lastModified
 */
 
 
object ListInstance{
    private val list = ArrayList<FlashMob>()
    val gson = GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create()
    val adapter = FlashMobAdapter(list)
    var sp: SharedPreferences? = null

    fun add(fm: FlashMob){
        if (!list.contains(fm)){
            Log.d("add", "in: ${fm.name}")
            remove(fm.name)
            list.add(fm)
            sort()
            adapter.notifyDataSetChanged()
            sp?.change { putString(fm.name, gson.toJson(fm, FlashMob::class.java)) }
        }
    }

    fun get(pos: Int) = list[pos]

    fun remove(name: String){
        Log.d("rm", "in: $name")
        list.removeAll(list.filter { it.name == name })
        sp?.change { remove(name) }
    }

    fun setContext(context: Context){
        sp = context.getSharedPreferences("list", Context.MODE_PRIVATE)
        load()
    }

    fun load(){
        sp?.all?.keys?.forEach { add(gson.fromJson(sp?.getString(it, "null"), FlashMob::class.java)) }
        sort()
        adapter.notifyDataSetChanged()
    }

    fun sort() = Collections.sort(list)
}