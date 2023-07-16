package com.uce.edu.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.uce.edu.R
import com.uce.edu.databinding.ActivityMainBinding
import com.uce.edu.logic.validator.LoginValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

//es una extension general; es una mini base de datos (clave-valor)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    // At the top level of your kotlin file:


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()

//        val db = DispositivosMoviles.getDBInstance()
//        db.marvelDao()
    }

    fun initClass() {

        var btnIng = binding.btnIngresar.setOnClickListener {
            val check = LoginValidator().checkLogin(
                binding.txtOrreo.text.toString(),
                binding.txtPass.text.toString()
            )
            if (check) {
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.txtOrreo.text.toString())
                }


                var intent = Intent(this, SegundaPantalla::class.java)

                intent.putExtra("var1", "")
                intent.putExtra("var2", 2)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun saveDataStore(stringData:String){
        dataStore.edit {prefs->//hace una funcion suspendida y se tiene que ejecutar en una corrutina
            prefs[stringPreferencesKey("usuario")]= stringData
            prefs[stringPreferencesKey("session")]= UUID.randomUUID().toString() //UUI universal User Identifier
            prefs[stringPreferencesKey("email")]= "dispomoviles@uce.edu.ec"
        }
    }

}