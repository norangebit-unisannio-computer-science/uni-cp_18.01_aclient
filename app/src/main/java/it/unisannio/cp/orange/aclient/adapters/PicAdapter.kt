package it.unisannio.cp.orange.aclient.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.network.rest.Path
import kotlinx.android.synthetic.main.card_pic.view.*


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 25/12/17
 *  Last Modified: $file.lastModified
 */


class PicAdapter(private val list: ArrayList<String>, var name: String = ""): RecyclerView.Adapter<PicHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PicHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.card_pic, parent, false)
        return PicHolder(v)
    }

    override fun onBindViewHolder(holder: PicHolder?, position: Int) {
        holder?.title?.text = list[position].substringBefore("_")
        Glide.with(holder?.itemView?.context).load("${Path.ip}/$name/photo/${list[position]}")
                .placeholder(R.color.placeholder).into(holder?.pic)
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
