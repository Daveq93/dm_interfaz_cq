package com.uce.edu.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uce.edu.R
import com.uce.edu.databinding.FragmentFirstBinding
import com.uce.edu.data.marvel.MarvelChars
import com.uce.edu.logic.jikanLogic.JikanAnimeLogic
import com.uce.edu.logic.list.ListItems
import com.uce.edu.ui.activities.DetailsMarvelItem
import com.uce.edu.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>("Karen", "Maria", "Grace", "Joa", "Pepito de los palotes")

        val adapter1 = ArrayAdapter<String>(
            requireActivity(),
            //el simple spinner no es de nadie :v
            R.layout.simple_spinner,
            names
        )

        binding.spinner.adapter = adapter1

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener{
            chargeDataRV()
            binding.rvSwipe.isRefreshing=false
        }
       // binding.listView.adapter = adapter


    }
    //Un intent se encuentra en un activity o un fragment

    //una analogia a serializacion es el parcelable, es  mas eficiente pero mas dificil de implementar
    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name",item)
        startActivity(i)
    }

    fun chargeDataRV(){

        lifecycleScope.launch(Dispatchers.IO){
            val rvAdapter = MarvelAdapter(
                JikanAnimeLogic().getAllAnimes()
            ){sendMarvelItem(it)}

           withContext(Dispatchers.Main){
               val rvMarvel = binding.rvMarvelChars
               with(rvMarvel){
                   this.adapter = rvAdapter
                   this.layoutManager= LinearLayoutManager(
                       requireActivity(),LinearLayoutManager.VERTICAL,false
                   )
               }
           }

        }


    }

}