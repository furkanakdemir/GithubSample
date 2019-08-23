package net.furkanakdemir.githubsample.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.fragment_repo_list.*
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.ui.base.BaseFragment
import net.furkanakdemir.githubsample.ui.data.Repo
import timber.log.Timber
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

        repoViewModel = ViewModelProviders.of(this, viewModelFactory).get()

        repoViewModel.repoLiveData.observe(viewLifecycleOwner, Observer {
            repoAdapter.update(it)
        })

        button.setOnClickListener {
            val username = textInputLayout.editText?.text.toString()
            repoViewModel.search(username)
        }

        repoAdapter = RepoAdapter(this)
        repoRecyclerView.adapter = repoAdapter

    }

    override fun onRepoClicked(repo: Repo) {
        Timber.i("Repo ${repo.id} clicked")

        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(repo)
        findNavController(this).navigate(action)
    }
}