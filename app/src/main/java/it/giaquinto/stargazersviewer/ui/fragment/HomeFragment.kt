package it.giaquinto.stargazersviewer.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.databinding.FragmentHomeBinding
import it.giaquinto.stargazersviewer.ui.adapter.RecyclerAdapter
import it.giaquinto.stargazersviewer.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private val adapter: RecyclerAdapter by lazy {
        RecyclerAdapter(listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingPreparation()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                binding.apply {
                    textviewFirst.text = it.informationMessage.msg

                    if (it.isFetchingUsers) {
                        progressBar.visibility = View.VISIBLE
                    }

                    if (it.users.isNotEmpty()) {
                        adapter.list = it.users
                    }
                }
            }
        }


    }

    private fun bindingPreparation() = binding.apply {
        lifecycleOwner = this@HomeFragment
        homeViewModel = viewModel
        recyclerView.adapter = adapter

        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}