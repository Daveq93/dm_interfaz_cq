package com.uce.edu.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uce.edu.R
import com.uce.edu.databinding.FragmentFirstBinding
import com.uce.edu.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
//FragmentFirstBinding
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_first, container, false)
        return binding.root
    }
}