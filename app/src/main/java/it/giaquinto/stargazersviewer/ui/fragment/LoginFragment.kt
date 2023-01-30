package it.giaquinto.stargazersviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.databinding.FragmentLoginBinding
import it.giaquinto.stargazersviewer.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingPreparation()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                    if (it.isLoading) {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    if (it.loginOk) {
                        findNavController().navigate(R.id.action_LoginFragment_to_FirstFragment)
                    }
                }
            }
        }
    }


    private fun bindingPreparation() = binding.apply {
        lifecycleOwner = this@LoginFragment
        bindViewModel = viewModel
    }
}