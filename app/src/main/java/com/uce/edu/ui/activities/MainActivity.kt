package com.uce.edu.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.location.Geocoder
import android.location.Location
import android.media.audiofx.BassBoost
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
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

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient//nos da el acceso a la ubicacion

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onStart() {
        super.onStart()
        initClass()

//        val db = DispositivosMoviles.getDBInstance()
//        db.marvelDao()
    }

    @SuppressLint("MissingPermission")
    fun initClass() {

        var btnIng = binding.btnIngresar.setOnClickListener {
            val check = LoginValidator().checkLogin(
                binding.txtCorreo.text.toString(),
                binding.txtPass.text.toString()
            )
            if (check) {
                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtCorreo.text.toString())
                }


                var intent = Intent(this, SegundaPantalla::class.java)

                intent.putExtra("var1", "")
                intent.putExtra("var2", 2)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        val locationContract =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                when (isGranted) {
                    true -> {
//                        val task = fusedLocationProviderClient.lastLocation
//
//                        task.addOnSuccessListener {
//                            if (task.result != null) {
//                                Snackbar.make(
//                                    binding.txtCorreo,
//                                    "latitud ${it.latitude}, longitud ${it.longitude}",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Snackbar.make(
//                                    binding.txtCorreo,
//                                    "Encienda en GPS MMV",
//                                    Snackbar.LENGTH_LONG
//                                ).show()
//                            }
//                        }
                        //--------------------------------
                        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                            it.longitude
                            it.latitude

                            val a= Geocoder(this)//obtenemos menos precisas, por ejem, pais, sector, etc
                            a.getFromLocation(it.latitude,it.longitude,1)

                        }
                    }
                     //me sirve para informar al usuario para informar del porque de la necesidad de activar la ubicacion
                    //el access coarse sirve para saver algo no tan concreto en cuando a la ubicacion=> pais, sector, .. etc.
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)->{
                       // Snackbar.make(binding.txtCorreo, "Activa la ubicacion mmv..", Snackbar.LENGTH_LONG).show()
                    }

                    false -> {
                       // Snackbar.make(binding.txtCorreo, "Denegado", Snackbar.LENGTH_LONG).show()
                    }

//                    else -> {
//                        Snackbar.make(binding.txtCorreo, "Denegado", Snackbar.LENGTH_LONG).show()
//                    }
                }

            }
        binding.btnTwitter.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:-0.200628,-78.5786066"))
//             startActivity(intent)//no se que app va a abrir la url
            //ACTION_SEARCH
            // -------------------------
//            val intentX = Intent(Intent.ACTION_WEB_SEARCH)
//            intentX.setClassName(
//                "com.google.android.googlequicksearchbox",
//                "com.google.android.googlequicksearchbox.SearchActivity"
//            )
//            intentX.putExtra(SearchManager.QUERY, "UCE")
//            startActivity(intentX)
            //----------------------------------
            //El access fine es para saber la ubicacion excacta
            locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->
            val sn = Snackbar.make(binding.txtCorreo, "", Snackbar.LENGTH_LONG)
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
            val sn = Snackbar.make(binding.txtCorreo, "", Snackbar.LENGTH_LONG)
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val smg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .toString()
                    if (smg.isNotEmpty()) {
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, smg.toString())
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