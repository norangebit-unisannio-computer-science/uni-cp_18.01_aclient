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

package it.unisannio.cp.orange.aclient.adapters

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.model.RequestPermission
import it.unisannio.cp.orange.aclient.network.rest.Path
import it.unisannio.cp.orange.aclient.util.toast
import it.unisannio.cp.orange.aclient.services.DownloadService
import it.unisannio.cp.orange.aclient.util.Util
import it.unisannio.cp.orange.aclient.util.checkPermission
import kotlinx.android.synthetic.main.card_pic.view.*
import java.io.File


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 25/12/17
 *  Last Modified: $file.lastModified
 */


class PicAdapter(private val list: ArrayList<String>, val reqPer: RequestPermission, var name: String = ""): RecyclerView.Adapter<PicHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PicHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.card_pic, parent, false)
        return PicHolder(v)
    }

    override fun onBindViewHolder(holder: PicHolder?, position: Int) {
        val context = holder?.itemView?.context

        holder?.title?.text = list[position].substringBefore("_")

        Glide.with(context).load("${Path.ip}/$name/photo/${list[position]}")
                .placeholder(R.color.placeholder).into(holder?.pic)

        holder?.icon?.setOnClickListener{
            if(context?.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ?: false)
                downloadPhoto(position, context)
            else
                reqPer.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    fun downloadPhoto(pos: Int, context: Context?){
          if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
              val dir = File("${Environment.getExternalStorageDirectory()}/${Environment.DIRECTORY_PICTURES}", "flashmob")

              if(!dir.exists())
                  dir.mkdir()

              val outFile = File(dir, list[pos])
              if(outFile.exists())
                  context?.toast(R.string.already_exist)
              else{
                  val downloadIntent = Intent(context, DownloadService::class.java)
                  downloadIntent.putExtra(Util.KEY_URL, "${Path.ip}/$name/photo/${list[pos]}")
                  downloadIntent.putExtra(Util.KEY_FILE, outFile)
                  context?.startService(downloadIntent)
              }

          }else
              context?.toast(R.string.sd_unmount)
    }
}

class PicHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var card: CardView? = null
    var title: TextView? = null
    var icon: ImageView? = null
    var pic: ImageView? = null

    init {
        card = itemView.card_pic
        title = itemView.pic_title
        icon = itemView.download
        pic = itemView.pic
    }
}
