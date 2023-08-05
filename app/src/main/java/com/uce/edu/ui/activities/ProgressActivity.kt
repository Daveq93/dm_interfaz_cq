package com.uce.edu.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.uce.edu.R
import com.uce.edu.databinding.ActivityProgressBinding
import com.uce.edu.ui.utilities.viewmodels.ProgressViewModel
//import com.example.primera_view.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding

    private val progressviewmodel by viewModels<ProgressViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Livedata
        progressviewmodel.progressState.observe(this, Observer {
            binding.progressBar.visibility = it
        })

        progressviewmodel.items.observe(this, Observer {
            Toast.makeText(this, it[1].name, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,NotificationActivity::class.java))
        })

        // Listeners
        binding.btnProceso.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO){
                progressviewmodel.getMarvelChars(0,90)
            }
        }

        binding.btnProceso1.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO){
                progressviewmodel.getMarvelChars(0,90)
            }
//            lifecycleScope.launch(Dispatchers.Main){
//                binding.progressBar.visibility = View.VISIBLE
////                lifecycleScope.launch(Dispatchers.IO) {
//                delay(6000)
////                }
//                binding.progressBar.visibility = View.GONE
//            }

//            progressviewmodel.processBackground(6000)

        }

    }

}