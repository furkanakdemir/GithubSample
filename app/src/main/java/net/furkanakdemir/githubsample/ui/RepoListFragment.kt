package net.furkanakdemir.githubsample.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.fragment_repo_list.*
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.ext.hide
import net.furkanakdemir.githubsample.ext.hideKeyboard
import net.furkanakdemir.githubsample.ext.show
import net.furkanakdemir.githubsample.ui.base.BaseFragment
import net.furkanakdemir.githubsample.ui.data.Repo
import javax.inject.Inject

class RepoListFragment : BaseFragment(), RepoAdapter.OnRepoCallback {

    private lateinit var repoAdapter: RepoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: RepoViewModel

    override val layoutId: Int
        get() = R.layout.fragment_repo_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showInitialEmptyText()

        textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // No-op
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No-op
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text.isNullOrEmpty()) {
                    showInitialEmptyText()
                }
            }
        })

        repoViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get()

        repoViewModel.repoLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Result.Success -> {
                    repoAdapter.update(it.repos)
                    showContent()
                }
                is Result.Loading -> showLoading()
                is Result.Failure -> showNotFound()
            }
        })

        repoViewModel.favRepoLiveData.observe(viewLifecycleOwner, Observer {
            repoAdapter.notifyDataSetChanged()
        })

        submitButton.setOnClickListener {
            val username = textInputLayout.editText?.text.toString()
            repoViewModel.search(username)

            hideKeyboard()
        }

        repoAdapter = RepoAdapter(this)
        repoRecyclerView.adapter = repoAdapter
    }

    private fun showLoading() {
        progressBar.show()
        repoRecyclerView.hide()
        emptyTextView.hide()
    }

    private fun showContent() {
        progressBar.hide()
        repoRecyclerView.show()
        emptyTextView.hide()
    }

    private fun showInitialEmptyText() {
        emptyTextView.text = getString(R.string.list_empty_initial)
        progressBar.hide()
        repoRecyclerView.hide()
        emptyTextView.show()
    }

    private fun showNotFound() {
        emptyTextView.text = getString(R.string.list_empty_not_found)
        progressBar.hide()
        repoRecyclerView.hide()
        emptyTextView.show()
    }

    override fun onRepoClicked(repo: Repo) {
        repoViewModel.selectRepo(repo)

        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(repo)
        findNavController(this).navigate(action)
    }
}
