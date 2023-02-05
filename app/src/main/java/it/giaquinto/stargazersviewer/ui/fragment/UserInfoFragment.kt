package it.giaquinto.stargazersviewer.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.data.model.RepoUserModel
import it.giaquinto.stargazersviewer.databinding.FragmentUserInfoBinding
import it.giaquinto.stargazersviewer.databinding.RepoUserLayoutBinding
import it.giaquinto.stargazersviewer.ui.adapter.RecyclerAdapter
import it.giaquinto.stargazersviewer.ui.adapter.RepoUserRecyclerAdapter
import it.giaquinto.stargazersviewer.ui.viewmodel.UserInfoViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    private val userInfoViewModel: UserInfoViewModel by viewModels()

    private lateinit var _binding: FragmentUserInfoBinding
    private lateinit var _adapter: RecyclerAdapter<RepoUserModel, RepoUserLayoutBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingPreparation()

        viewLifecycleOwner.lifecycleScope.launch {
            userInfoViewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {

                if (it.toastMessage?.isNotEmpty() == true)
                    Snackbar.make(_binding.root, it.toastMessage, Snackbar.LENGTH_SHORT).show()

                if (!it.isFetchingUsers) {
                    it.userInfo?.let { userInfo ->
                        _binding.user = userInfo
                        _binding.executePendingBindings()
                        Picasso.get().load(userInfo.avatarUrl).into(_binding.avatarView)
                    }
                }

                if(it.repoUserList?.isNotEmpty() == true) {
                    _adapter.updateList(it.repoUserList)
                }
            }
        }

        arguments?.getString("userName")?.let {
            userInfoViewModel.fetchUser(it)
        }
    }

    private fun bindingPreparation() = _binding.apply {
        lifecycleOwner = this@UserInfoFragment
        viewModel = userInfoViewModel
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            _adapter = RepoUserRecyclerAdapter(mutableListOf())
            adapter = _adapter
        }
    }
}