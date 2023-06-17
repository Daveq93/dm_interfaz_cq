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
//        var btnVol = binding.btnVolver.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//
//            startActivity(intent)
//        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
//                    var suma: Int = 0
//                    for (i in listOf(1, 2, 3)) {
//                        suma += i
//                    }
//                    Snackbar.make(
//                        binding.textView,
//                        "Entramos a inicio " + suma,
//                        Snackbar.LENGTH_LONG
//                    ).show()

//                    val frag = FirstFragment()
//                    val transaction =
//                        supportFragmentManager.beginTransaction()//necesito un contenedor y un objeto que se va a alojar en el contenedor
//                    //transaction.replace(binding.frmContainer.id,frag)//en el activity, para que se carge solo uno
//                    transaction.add(
//                        binding.frmContainer.id,
//                        frag
//                    )//en el activity, para que se añada a  la pila
//                    transaction.addToBackStack(null) //esto lo usamos si tenemos en add
//                                //como se añade a la pila, por cada vez que presionamos el boton donde se carga el fragment
//                                   //tendiramos que presionar el numero igual de veces volver atras, porque se va agregando cada vez a la pila
//                    transaction.commit()

                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )

                    true
                }

                R.id.favoritos -> {
                    // Respond to navigation item 1 click
//                    var suma: Int = 0
//                    for (i in listOf(8, 27, 3)) {
//                        suma += i
//                    }
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