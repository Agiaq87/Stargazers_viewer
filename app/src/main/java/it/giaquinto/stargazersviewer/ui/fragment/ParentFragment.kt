package it.giaquinto.stargazersviewer.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class ParentFragment: Fragment() {

    fun sendMessage(
        view: View,
        message: String
    ) {

    }
}