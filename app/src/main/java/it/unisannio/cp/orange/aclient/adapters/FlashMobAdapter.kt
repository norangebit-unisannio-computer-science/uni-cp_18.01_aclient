package it.unisannio.cp.orange.aclient.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
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
import it.unisannio.cp.orange.aclient.broadcastReceivers.AlarmReceiver
import it.unisannio.cp.orange.aclient.services.NotificationPublisherService
import it.unisannio.cp.orange.aclient.util.Util
import kotlinx.android.synthetic.main.card_flashmob.view.*
import java.util.*


/*
 *  Author: Raffaele Mignone
 *  Mat: 863/747
 *  Date: 25/12/17
 *  Last Modified: $file.lastModified
 */


class FlashMobAdapter(private val list: ArrayList<FlashMob>): RecyclerView.Adapter<FlashMobHolder>() {

    companion object {
        val MINUTE = 60000
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FlashMobHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.card_flashmob, parent, false)
        return FlashMobHolder(v)
    }

    override fun onBindViewHolder(holder: FlashMobHolder?, position: Int) {
        val fm = list[position]
        val context = holder?.itemView?.context
        holder?.title?.text = list[position].name
        Glide.with(holder?.itemView?.context).load("${Path.ip}/${fm.name}/${Path.COVER}")
                .placeholder(R.color.placeholder).into(holder?.cover)

        holder?.icon?.visibility = if (fm.start.time > System.currentTimeMillis()) View.VISIBLE else View.INVISIBLE //TODO fix orario

        holder?.icon?.setEventListener( object : SparkEventListener {
            override fun onEventAnimationStart(button: ImageView?, state: Boolean) {
            }

            override fun onEventAnimationEnd(button: ImageView?, state: Boolean) {
                Toast.makeText(context, R.string.alarm_set, Toast.LENGTH_SHORT).show()
            }

            override fun onEvent(button: ImageView, state: Boolean) {
                /*
                if (state){
                    val broadcast = Intent(context, AlarmReceiver::class.java)
                    broadcast.putExtra(Util.KEY_POS, holder.adapterPosition)
                    broadcast.putExtra(Util.KEY_TIME, NotificationPublisherService.NOW)
                    val pending = PendingIntent.getBroadcast(context, Util.CODE_ALARM_RECEIVER, broadcast, PendingIntent.FLAG_UPDATE_CURRENT)
                    if(context?.getSharedPreferences(Util.SP_SETTINGS, Context.MODE_PRIVATE)?.getBoolean(Util.KEY_BATTERY_SAVE, true) ?: true){
                        val elapse = fm.start.time - System.currentTimeMillis()
                        if(elapse < 15*MINUTE){
                            broadcast.putExtra(Util.KEY_TIME, NotificationPublisherService.SOON)
                            context?.sendBroadcast(broadcast)
                        }
                        else {

                            val extra = Bundle()
                            extra.putInt(Util.KEY_POS, holder.adapterPosition)
                            extra.putInt(Util.KEY_TIME, NotificationPublisherService.NOW)

                            Log.d("firebase", "in")

                            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
                            val builder = dispatcher.newJobBuilder()
                            builder.setService(AlarmJobService::class.java)
                            builder.tag = "test"
                            builder.isRecurring = false
                            builder.trigger = Trigger.executionWindow((elapse-10).toInt(), elapse.toInt())
                            builder.extras = extra
                            dispatcher.schedule(builder.build())
                        }
                    }else{
                        val alarmMnager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        alarmMnager.set(AlarmManager.RTC_WAKEUP, fm.start.time, pending)
                    }
                }**/

                val broadcast = Intent(context, AlarmReceiver::class.java)
                broadcast.putExtra(Util.KEY_POS, holder.adapterPosition)
                broadcast.putExtra(Util.KEY_TIME, NotificationPublisherService.NOW)
                val pending = PendingIntent.getBroadcast(context, Util.CODE_ALARM_RECEIVER, broadcast, PendingIntent.FLAG_UPDATE_CURRENT)
                val alarmMnager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmMnager.set(AlarmManager.RTC_WAKEUP, fm.start.time, pending)
            }
        })

        holder?.card?.setOnClickListener {
            val intent = Intent(context, DetailFlashMobActivity::class.java)
            intent.putExtra(Util.KEY_POS, position)
            startActivity(context, intent, null)
        }
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
