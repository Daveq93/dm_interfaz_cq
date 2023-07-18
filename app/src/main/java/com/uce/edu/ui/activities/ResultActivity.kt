package com.uce.edu.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uce.edu.R
import com.uce.edu.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnResult.setOnClickListener{

             setResult(RESULT_OK)
            finish()//matamos el estado, se ejecuta el onDestroy
        }

        binding.btnResultFalse.setOnClickListener{
              setResult(RESULT_CANCELED)
            finish()//matamos el estado, se ejecuta el onDestroy
        }
    }


}