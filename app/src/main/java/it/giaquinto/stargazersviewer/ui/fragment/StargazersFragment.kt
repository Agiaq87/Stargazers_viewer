package it.giaquinto.stargazersviewer.ui.fragment

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.data.model.ErrorHintEditText
import it.giaquinto.stargazersviewer.data.model.StargazersModel
import it.giaquinto.stargazersviewer.data.model.UserInteraction
import it.giaquinto.stargazersviewer.databinding.FragmentStargazersBinding
import it.giaquinto.stargazersviewer.ui.adapter.RecyclerAdapter
import it.giaquinto.stargazersviewer.ui.adapter.StargazersRecyclerAdapter
import it.giaquinto.stargazersviewer.ui.viewmodel.StarViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StargazersFragment : Fragment() {

    private val starViewModel: StarViewModel by viewModels()

    private lateinit var _binding: FragmentStargazersBinding
    private lateinit var _adapter: StargazersRecyclerAdapter
    private lateinit var _inputMethodManager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_stargazers, container, false)
        return _binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingPreparation()

        _inputMethodManager = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)

        viewLifecycleOwner.lifecycleScope.launch {
            starViewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { uiState ->

                if (uiState.toastMessage?.isNotEmpty() == true) {
                    Snackbar.make(_binding.root, uiState.toastMessage, Snackbar.LENGTH_SHORT).show()
                }

                if (uiState.stargazersList?.isNotEmpty() == true) {
                    _adapter.updateList(uiState.stargazersList)
                } else {
                    _adapter.updateList(listOf())
                }

                when {

                    uiState.userInteraction == UserInteraction.WAITING_RESPONSE -> resetKeyBoard()

                    uiState.errorHintEditText == ErrorHintEditText.USER -> {
                        _binding.editTextTextEmailAddress.error = getString(R.string.user_needed)
                        uiState.toastMessage?.let { it1 -> Snackbar.make(_binding.root, it1, Snackbar.LENGTH_SHORT).show() }
                    }
                    uiState.errorHintEditText == ErrorHintEditText.REPO -> {
                        _binding.editTextTextPersonName.error = getString(R.string.repo_needed)
                        uiState.toastMessage?.let { it1 -> Snackbar.make(_binding.root, it1, Snackbar.LENGTH_SHORT).show() }
                    }
                }
            }
        }
    }

    private fun bindingPreparation() = with(_binding) {
        lifecycleOwner = this@StargazersFragment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        _adapter = StargazersRecyclerAdapter(mutableListOf()) {
            findNavController().navigate(
                R.id.action_StargazersFragment_to_UserFragment,
                bundleOf("userName" to it.login)
            )
        }
        recyclerView.adapter = _adapter
        viewModel = starViewModel
    }

    private fun resetKeyBoard() = with(_inputMethodManager) {
        _inputMethodManager.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
    }
}