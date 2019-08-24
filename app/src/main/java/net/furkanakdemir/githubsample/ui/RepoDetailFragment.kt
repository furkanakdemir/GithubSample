package net.furkanakdemir.githubsample.ui


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.image.ImageLoader
import net.furkanakdemir.githubsample.ui.base.BaseFragment
import javax.inject.Inject


class RepoDetailFragment : BaseFragment() {


    private var menuItem: MenuItem? = null
    private var menu: Menu? = null
    val repoArgs: RepoDetailFragmentArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: RepoViewModel

    var test = false


    override val layoutId: Int
        get() = R.layout.fragment_repo_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get()

        repoViewModel.favRepoLiveData.observe(viewLifecycleOwner, Observer {
            setFavoriteIcon(it)
        })


        val repo = repoArgs.repo

        imageLoader.load(userImageView, repo.owner?.imageUrl ?: "")
        userTextView.text = repo.owner?.name
        starsTextView.text = repo.starCount.toString()
        issuesTextView.text = repo.issuesCount.toString()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu.findItem(R.id.favoriteItem)

        repoViewModel.setFavoriteStatus()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoriteItem -> {
                repoViewModel.toggleFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setFavoriteIcon(isFavorite: Boolean) {

        if (isFavorite) {
            menuItem?.icon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite) }
        } else {
            menuItem?.icon =
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_not_favorite) }
        }


    }
}
