package com.uce.edu.ui.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uce.edu.R
import com.uce.edu.databinding.ActivitySegundaPantallaBinding
class SegundaPantalla : AppCompatActivity() {

    private lateinit var binding:ActivitySegundaPantallaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_pantalla)
        binding = ActivitySegundaPantallaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClas()
    }

    fun initClas(){

        var btnVol = binding.btnVolver.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)

            startActivity(intent)
        }

    }

}