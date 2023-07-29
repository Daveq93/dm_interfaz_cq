package com.uce.edu.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.uce.edu.R
import com.uce.edu.databinding.ActivityNotificationBinding


class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //error en el binding
        binding.btnNotification.setOnClickListener {
            sendNotification()
        }

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
