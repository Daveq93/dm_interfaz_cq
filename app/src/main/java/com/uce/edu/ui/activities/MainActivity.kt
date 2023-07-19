package com.uce.edu.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.media.audiofx.BassBoost
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.uce.edu.R
import com.uce.edu.databinding.ActivityMainBinding
import com.uce.edu.logic.validator.LoginValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
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
                lifecycleScope.launch(Dispatchers.IO) {
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

        binding.btnTwitter.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:-0.200628,-78.5786066"))
//             startActivity(intent)//no se que app va a abrir la url
            //ACTION_SEARCH
            val intentX = Intent(Intent.ACTION_WEB_SEARCH)
            intentX.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intentX.putExtra(SearchManager.QUERY, "UCE")

            startActivity(intentX)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->
            val sn = Snackbar.make(binding.txtOrreo, "", Snackbar.LENGTH_LONG)
            var col: Int = resources.getColor(R.color.starCard)
            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    // Log.d("UCE","Resultado exitoso")
                    // Snackbar.make(binding.txtOrreo,"Resultadp exitoso",Snackbar.LENGTH_LONG).show()
                    // "Resultado exitoso"
                    sn.setBackgroundTint(resources.getColor(R.color.starCard))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                RESULT_CANCELED -> {
                    // Log.d("UCE", "Resultado fallido")
                    //  Snackbar.make(binding.txtOrreo,"Resultadp fallido",Snackbar.LENGTH_LONG).show()
                    // "Resultado fallido"
                    sn.setBackgroundTint(resources.getColor(R.color.starCard))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                else -> {
                    //  Log.d("UCE","Resultado dudoso")

                    "Resultado dudoso"
                }
            }

            sn.setText(message)
            sn.show()
        }
        val speechToText = registerForActivityResult(StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(binding.txtOrreo, "", Snackbar.LENGTH_LONG)
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val smg = activityResult
                            .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            .toString()
                    if(smg.isNotEmpty()){
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY,smg.toString())
                        startActivity(intent)
                    }

                }

                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.starCard))
                }

                else -> {
                    message = "Proceso error"
                    sn.setBackgroundTint(resources.getColor(R.color.starCard))
                }
            }
            sn.setText(message)
            sn.show()
        }
        binding.btnFacebook.setOnClickListener() {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //lenguaje general, puede buscar donde sea
            )

            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di algo mmv..."
            )
            speechToText.launch(intentSpeech)
        }

//        binding.btnFacebook.setOnClickListener {
//            val resIntent = Intent(this, ResultActivity::class.java)
//            appResultLocal.launch(resIntent)
//        }


    }
    //https://api.whatsapp.com/send?phone=593 &text=

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->//hace una funcion suspendida y se tiene que ejecutar en una corrutina
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] =
                UUID.randomUUID().toString() //UUI universal User Identifier
            prefs[stringPreferencesKey("email")] = "dispomoviles@uce.edu.ec"
        }
    }


}