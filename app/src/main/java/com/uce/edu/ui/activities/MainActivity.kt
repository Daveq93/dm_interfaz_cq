package com.uce.edu.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient


import com.google.android.material.snackbar.Snackbar
import com.uce.edu.R
import com.uce.edu.databinding.ActivityMainBinding
import com.uce.edu.logic.validator.LoginValidator
import com.uce.edu.ui.utilities.MyLocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID


//es una extension general; es una mini base de datos (clave-valor)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    //ubicacion y gps
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient//nos da el acceso a la ubicacion
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBak: LocationCallback

    // private var currentLocation: Location? = null
    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest

    private val speechToText =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
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

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {

//                    val task = fusedLocationProviderClient.lastLocation
//                    task.addOnSuccessListener { location ->
//                        val alert = AlertDialog.Builder(this)
//                        alert.apply {
//                            setTitle("Alerta")
//                            setMessage("Existe un problema con el sistema de posicionamiento")
//                            setPositiveButton("ok") { dialog, id ->
//                                dialog.dismiss()
//                            }
//                            setNegativeButton("cancelar"){dialog,id->
//                                dialog.dismiss()
//                            }
//                            setCancelable(false)//hasta que no se de en ok, no va a salir
//                        }.create()
//                        alert.show()
//
//                        fusedLocationProviderClient.requestLocationUpdates(
//                            locationRequest,
//                            locationCallBak,
//                            Looper.getMainLooper()
//                        )
                    client.checkLocationSettings(locationSettingsRequest).apply {
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener { location ->
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallBak,
                                    Looper.getMainLooper()

                                )
                            }
                        }
                        addOnFailureListener { ex ->
                            //  startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            if (ex is ResolvableApiException) {
                                ex.startResolutionForResult(
                                    this@MainActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }
                        }


                    }

//                    task.addOnFailureListener {ex->
//                        if(ex is ResolvableApiException){
//                            ex.startResolutionForResult(
//                                this@MainActivity,
//                               LocationSettingsStatusCodes.RESOLUTION_REQUIRED
//                            )
//                        }
//                    }
                }
                //me sirve para informar al usuario para informar del porque de la necesidad de activar la ubicacion
                //el access coarse sirve para saver algo no tan concreto en cuando a la ubicacion=> pais, sector, .. etc.
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000
        ).build()  //condiciones para que nos actialice

        locationCallBak = object : LocationCallback() {
            override fun onLocationResult(lacationResult: LocationResult) {
                super.onLocationResult(lacationResult)

                if (lacationResult != null) {
                    lacationResult.locations.forEach { location ->
                        //currentLocation = location
                        Log.d(
                            "UCE",
                            "Ubicacion: latitud ${location.latitude}, longitud ${location.longitude}"
                        )
                    }
                }
            }
        }
        locationSettingsRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
        client = LocationServices.getSettingsClient(this)
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


    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallBak)
    }

    private fun test() {
        var location = MyLocationManager(this)
        // location.context= this
        location.getUserLocation()
    }

}