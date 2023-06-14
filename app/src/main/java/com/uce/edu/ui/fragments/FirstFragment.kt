package com.uce.edu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.uce.edu.R
import com.uce.edu.databinding.FragmentFirstBinding

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

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            //el simple spinner no es de nadie :v
            R.layout.simple_spinner,
            names
        )

        binding.spinner.adapter = adapter
        binding.listView.adapter = adapter
    }
}