package com.uce.edu.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.uce.edu.ui.fragments.FirstFragment

class FragmentsManager {

    fun replaceFragment(
        manager: FragmentManager,
        container: Int,
        fragment: Fragment
    ) {


        with(manager.beginTransaction()) {
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }


    }

}