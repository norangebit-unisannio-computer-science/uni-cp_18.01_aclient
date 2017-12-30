package it.unisannio.cp.orange.aclient.adapters

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener
import commons.FlashMob
import it.unisannio.cp.orange.aclient.R
import it.unisannio.cp.orange.aclient.network.rest.Path
import it.unisannio.cp.orange.aclient.activities.DetailFlashMobActivity
import kotlinx.android.synthetic.main.card_flashmob.view.*


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 25/12/17
 *  Last Modified: $file.lastModified
 */


class FlashMobAdapter(private val list: ArrayList<FlashMob>): RecyclerView.Adapter<FlashMobHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FlashMobHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.card_flashmob, parent, false)
        return FlashMobHolder(v)
    }

    override fun onBindViewHolder(holder: FlashMobHolder?, position: Int) {
        holder?.title?.text = list[position].name
        Glide.with(holder?.itemView?.context).load("${Path.ip}/${list[position].name}/${Path.COVER}")
                .placeholder(R.color.placeholder).into(holder?.cover)

        holder?.icon?.setEventListener( object : SparkEventListener {
            override fun onEventAnimationStart(button: ImageView?, state: Boolean) {
                Toast.makeText(holder.itemView.context, state.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onEventAnimationEnd(button: ImageView?, state: Boolean) {
                Toast.makeText(holder.itemView.context, state.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onEvent(button: ImageView, state: Boolean) {
            }
        })

        holder?.card?.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailFlashMobActivity::class.java)
            intent.putExtra(Key.POS, position)
            startActivity(holder.itemView.context, intent, null) }
    }
}

class FlashMobHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var card: CardView? = null
    var title: TextView? = null
    var icon: SparkButton? = null
    var cover: ImageView? = null

    init {
        card = itemView.card_fm
        title = itemView.title
        cover = itemView.cover
        icon = itemView.icon
    }
}

object Key{
    val POS = "it.unisannio.cp.orange.aclient.POS"
}
