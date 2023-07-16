package com.uce.edu.ui.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import com.google.android.material.snackbar.Snackbar
import com.uce.edu.R
import com.uce.edu.databinding.ActivitySegundaPantallaBinding
import com.uce.edu.ui.fragments.FirstFragment
import com.uce.edu.ui.fragments.SecondFragment
import com.uce.edu.ui.fragments.ThreeFragment
import com.uce.edu.ui.utilities.FragmentsManager

class SegundaPantalla : AppCompatActivity() {
    private lateinit var binding: ActivitySegundaPantallaBinding
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

    fun initClas() {

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )

                    true
                }

                R.id.favoritos -> {
//                    Snackbar.make(
//                        binding.textView,
//                        "Entramos a favoritos " + suma,
//                        Snackbar.LENGTH_LONG
//                    ).show()

                    ///----------------Fragment con SOLID ----------------
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )

                    true

                }

                R.id.chatgpt -> {

//                    var suma: Int = 0
//                    for (i in listOf(13, 2, 32)) {
//                        suma += i
//                    }
//                    Snackbar.make(
//                        binding.textView,
//                        "Entramos a chat GPT " + suma,
//                        Snackbar.LENGTH_LONG
//                    ).show()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThreeFragment()
                    )
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}