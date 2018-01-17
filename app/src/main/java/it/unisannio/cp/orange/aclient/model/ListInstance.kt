/*
 * Copyright (c)  2018 Raffaele Mignone
 *
 *        This file is part of  A Client
 *
 *        A Client is free software: you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *        the Free Software Foundation, either version 2 of the License, or
 *        (at your option) any later version.
 *
 *        A Client is distributed in the hope that it will be useful,
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 *        You should have received a copy of the GNU General Public License
 *        along with A Client.  If not, see <http://www.gnu.org/licenses/>.
 */

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