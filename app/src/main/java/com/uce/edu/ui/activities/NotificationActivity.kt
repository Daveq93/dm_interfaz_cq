package com.uce.edu.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.uce.edu.R
import com.uce.edu.databinding.ActivityNotificationBinding
import com.uce.edu.ui.utilities.BroadcasterNotifications
import java.util.Calendar


class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding


    val ca = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()



        //error en el binding
        binding.btnNotification.setOnClickListener {
            sendNotification()
        }

        binding.btnNotificationProgramada.setOnClickListener{
           // val calendar=Calendar.getInstance()

            val hora = binding.timePicker.hour
            val minutos = binding.timePicker.minute

           Toast.makeText(this,"la notificacion se activara a: $hora con $minutos minutos",Toast.LENGTH_SHORT).show()
          ca.set(Calendar.HOUR,hora)
            ca.set(Calendar.MINUTE,minutos)
            ca.set(Calendar.SECOND,0)

            sendNotificationTimePicker(ca.timeInMillis)
        }



    }
    private fun sendNotificationTimePicker(time:Long){
val myIntent = Intent(applicationContext,BroadcasterNotifications::class.java)
        val myPendingIntent = PendingIntent.getBroadcast(applicationContext,0,myIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,myPendingIntent)

    }

    //variable para chanel
    val CHANNEL: String = "Notificaciones"
    //esta fun salio de la pagina de android

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //val name = getString(R.string.channel_name)
            val name = "Variedades"
            //val descriptionText = getString(R.string.channel_description)
            val descriptionText = "Notificaciones simples de variedades"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            //el chanel id no tenemos pero lo colocamos el de variable
            //se puede tener varios canales, noticias, comentarios, mensajes....etc
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Registerque se cree en el sistema este canal
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }





    @SuppressLint("MissingPermission")
    fun sendNotification() {
        val noti = NotificationCompat.Builder(this, CHANNEL)
        //siempre van estos tres parametros
        noti.setContentTitle("Primera Notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setSmallIcon(R.drawable.baseline_message_24)
        //si no se coloca la prioridad es por deafult
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //para poder expandir la notificaion o estilo
        noti.setStyle(NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion para recordsar que estamos redactando en Android"))
        //clic derecho  -> show context -> supress mising con anotacion
        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }

    }

}












