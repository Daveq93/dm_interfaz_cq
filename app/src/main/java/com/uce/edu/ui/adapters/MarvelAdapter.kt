package com.uce.edu.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uce.edu.R
import com.uce.edu.databinding.MarvelCharactersBinding
import com.uce.edu.data.entity.marvel.MarvelChars

class MarvelAdapter(private val items: List<MarvelChars>,
                    private var fnClick:(MarvelChars) -> Unit ) ://no devuelve nada, analogia a void
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding : MarvelCharactersBinding= MarvelCharactersBinding.bind(view)


        fun render(item: MarvelChars, fnClick:(MarvelChars) -> Unit){
            println("Recibiendo a: ${item.name}")
            binding.imageView1.bringToFront()
            binding.txtName.text = item.name
            binding.txtComic.text =item.comic
            Picasso.get().load(item.image).into(binding.imageView1)

           itemView.setOnClickListener {
               // Snackbar.make(binding.imageView1,item.name,Snackbar.LENGTH_SHORT).show()
                   fnClick(item)
            }
        }
    }

    //Marvel view Holder es un nombre cualquiera
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters, parent, false))

    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position],fnClick)
    }

    override fun getItemCount(): Int = items.size

}