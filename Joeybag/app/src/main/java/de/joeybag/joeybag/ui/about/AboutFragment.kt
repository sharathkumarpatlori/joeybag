package de.joeybag.joeybag.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.joeybag.joeybag.R

class AboutFragment : Fragment() {

    private lateinit var binding: AboutFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_about, container, false)
        return binding.rootView
    }

}


