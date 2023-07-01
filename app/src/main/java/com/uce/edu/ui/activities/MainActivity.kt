package com.uce.edu.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.uce.edu.R
import com.uce.edu.databinding.ActivityMainBinding
import com.uce.edu.logic.validator.LoginValidator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart(){
        super.onStart()
        initClass()
    }

    fun initClass() {
        var btnIng = binding.btnIngresar.setOnClickListener {
            val check = LoginValidator().checkLogin(
                binding.txtOrreo.text.toString(),
                binding.txtPass.text.toString()
            )
            if (check){
                var intent = Intent(this,SegundaPantalla::class.java)

                intent.putExtra("var1","")
                intent.putExtra("var2",2)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }

        }



    }
}