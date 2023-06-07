package com.uce.edu.ui.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
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

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
                    var suma:Int=0
                    for(i in listOf(1,2,3)){
                        suma+=i
                    }
                    Snackbar.make(binding.textView, "Entramos a inicio "+suma, Snackbar.LENGTH_LONG).show()
                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 1 click
                    var suma:Int=0
                    for(i in listOf(8,27,3)){
                        suma+=i
                    }
                    Snackbar.make(binding.textView, "Entramos a favoritos "+suma, Snackbar.LENGTH_LONG).show()
                    true
                }
                R.id.chatgpt->{

                    var suma:Int=0
                    for(i in listOf(13,2,32)){
                        suma+=i
                    }
                    Snackbar.make(binding.textView, "Entramos a chat GPT "+suma, Snackbar.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }

    }

}