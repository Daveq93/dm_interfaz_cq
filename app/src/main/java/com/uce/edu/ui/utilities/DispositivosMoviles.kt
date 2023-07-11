package com.uce.edu.ui.utilities

import android.app.Application
import androidx.room.Room
import com.uce.edu.data.connections.MarvelConectionDB
import com.uce.edu.data.entity.marvel.characters.database.MarvelCharsDB

class DispositivosMoviles : Application() {


    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            MarvelConectionDB::class.java,
            "marvelDB"
        ).build()
    }


    companion object {
        private var db: MarvelConectionDB? = null

        fun getDBInstance() : MarvelConectionDB {
            return db!! //nunca va ser nula, ingreso a la app y se crea la isntancia
        }

    }
}