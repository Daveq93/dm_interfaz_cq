package com.uce.edu.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uce.edu.R
import com.uce.edu.databinding.FragmentFirstBinding
import com.uce.edu.logic.data.MarvelChars
import com.uce.edu.logic.jikanLogic.JikanAnimeLogic
import com.uce.edu.logic.marvelLogic.MarvelLogic
import com.uce.edu.ui.activities.DetailsMarvelItem
import com.uce.edu.ui.adapters.MarvelAdapter
import com.uce.edu.ui.utilities.DispositivosMoviles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {


    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter
    private var page = 1
    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    //para hacer en dos columnas
    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_first, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL,
            false
        )
        gManager = GridLayoutManager(requireActivity(), 2)
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
        chargeDataRV("cap")


//        binding.rvSwipe.setOnRefreshListener {
//            chargeDataRV(5)
//            binding.rvSwipe.isRefreshing = false
//        }
        binding.rvSwipe.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                chargeDataRV("cap")
                binding.rvSwipe.isRefreshing = false
                lmanager.scrollToPositionWithOffset(5, 20)
            }
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(
                        recyclerView,
                        dx,
                        dy
                    )//dy para abajo contando y mostrando, y la dx de izquierda a derecha
                    val v = lmanager.childCount//cuantos elementos tengo
                    val p = lmanager.findFirstVisibleItemPosition()//cual es mi posicion actual
                    val t = lmanager.itemCount//cuantos tengo en toal

                    if (dy > 0) {
                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.IO)) {
                                /*  val newItems = JikanAnimeLogic().getAllAnimes()*/
                                val newItems = MarvelLogic().getMarvelChars(
                                    name = "spider",
                                    limit = 20
                                )
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems((newItems))
                                }

                            }
                        }
                    }
                }
            })
        binding.txtFilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharsItems.filter { items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }


    }

    //Un intent se encuentra en un activity o un fragment
    //una analogia a serializacion es el parcelable, es  mas eficiente pero mas dificil de implementar
    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    /*
        fun corrtine(){
            lifecycleScope.launch(Dispatchers.Main){
                var name="dave"
              name= withContext(Dispatchers.IO){
                    name = "Maria"
                  return@withContext name
                }
                //aqui va el codigo que necesitemos
               // binding.card1Fragment.radius
            }
        }
        */
    fun chargeDataRV(search: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            rvAdapter.items = MarvelLogic().getAllMarvelChars(0, 99)

            withContext(Dispatchers.Main) {
                with(binding.rvMarvelChars) {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
            }
        }

        /* lifecycleScope.launch(Dispatchers.Main) {
            marvelCharsItems.addAll(withContext(Dispatchers.IO) {
                 return@withContext (MarvelLogic().getMarvelChars(
                     "spider", 20
                 ))
             })

             //rvAdapter = MarvelAdapter(marvelCharsItems, fnClick = { sendMarvelItem(it) })
             rvAdapter.items = marvelCharsItems
             binding.rvMarvelChars.apply {
                 this.adapter = rvAdapter
                 this.layoutManager = layoutManager
             }
           //  lmanager.scrollToPositionWithOffset(pos, 10)
         }*/

    }


    fun chargeDataRVDB(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharsItems.addAll(withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelCharDB().toMutableList()
            })

            if(marvelCharsItems.isEmpty()){
                marvelCharsItems = withContext(Dispatchers.IO){
                    return@withContext(MarvelLogic().getAllMarvelChars(0, page * 3)).toMutableList()
                }
            }

            withContext(Dispatchers.IO){
                MarvelLogic().insertMarvelCharstoDB(marvelCharsItems)
            }

            rvAdapter.items = marvelCharsItems
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
                gManager.scrollToPositionWithOffset(pos, 10)
            }

        }
        page++
    }

}