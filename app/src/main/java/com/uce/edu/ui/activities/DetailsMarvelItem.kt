package com.uce.edu.ui.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.uce.edu.databinding.ActivityDetailsMarvelItemBinding
import com.uce.edu.data.entity.marvel.characters.database.data.MarvelChars

class DetailsMarvelItem : AppCompatActivity() {

   private lateinit var binding : ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onStart() {
        super.onStart()

        val item = intent.getParcelableExtra<MarvelChars>("name")
        if(item != null){
            binding.txtName.text = item.name
            Picasso.get().load(item.image).into(binding.imageM)
            binding.txtComic.text=item.comic
           // binding.txtSinopsis.text=item.sinopsis
        }
    }
}