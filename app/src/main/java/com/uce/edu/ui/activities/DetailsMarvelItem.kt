package com.uce.edu.ui.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.uce.edu.databinding.ActivityDetailsMarvelItemBinding
import com.uce.edu.logic.data.MarvelChars

class DetailsMarvelItem : AppCompatActivity() {

   private lateinit var binding : ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onStart() {
        super.onStart()
//        var name:String?=""
//
//        intent.extras?.let{
//            name = it.getString("name")
//        }
//        if(!name.isNullOrEmpty()){
//            binding.textView2.text=name
//        }
        val item = intent.getParcelableExtra<MarvelChars>("name")
        if(item != null){
            binding.textView2.text = item.name
            Picasso.get().load(item.image).into(binding.imageM)
        }
    }
}