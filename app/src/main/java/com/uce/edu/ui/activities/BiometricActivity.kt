package com.uce.edu.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.uce.edu.R
import com.uce.edu.databinding.ActivityBiometricBinding
import com.uce.edu.ui.viewModels.BiometricViewModel
import kotlinx.coroutines.launch

class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding

    private val biometricViewModel by viewModels<BiometricViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAuth.setOnClickListener {
            lifecycleScope.launch {
                biometricViewModel.chargingData()
            }
        }

        biometricViewModel.isLoading.observe(this){
            isLoading->
            if(isLoading){
                binding.lytmain.visibility= View.GONE
                binding.lytmainCopia.visibility=View.VISIBLE
            }else{
                binding.lytmain.visibility= View.VISIBLE
                binding.lytmainCopia.visibility=View.GONE
            }
        }
        lifecycleScope.launch {
            biometricViewModel.chargingData()
        }

    }

    private fun aunticateBiometric() {

        if (checkBiometric()) {
            val executor = ContextCompat.getMainExecutor(this)

            val biometriPrompt = BiometricPrompt.PromptInfo.Builder()
                .setSubtitle("Ingrese su huella digital")
                .setTitle("Autenticacion Requerida")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                //.setNegativeButtonText("Cancelar")
                .build()
            val biometricManager = BiometricPrompt(this,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {

                        super.onAuthenticationSucceeded(result)
                        startActivity(Intent(this@BiometricActivity,
                        SegundaPantalla::class.java))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
            biometricManager.authenticate(biometriPrompt)
        } else {
            Snackbar.make(binding.btnAuth, "No cumple los requisitos", Snackbar.LENGTH_LONG).show()
        }


    }

    private fun checkBiometric(): Boolean {
        var returnValid: Boolean = false
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                returnValid = true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                returnValid = false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                returnValid = false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val intentPrompt = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                intentPrompt.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
                startActivity(intentPrompt)
                returnValid = false
            }
        }
        return returnValid
    }
}