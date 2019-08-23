package net.furkanakdemir.githubsample.ui


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.image.ImageLoader
import net.furkanakdemir.githubsample.ui.base.BaseFragment
import javax.inject.Inject

class RepoDetailFragment : BaseFragment() {


    val repoArgs: RepoDetailFragmentArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader


    override val layoutId: Int
        get() = R.layout.fragment_repo_detail


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = repoArgs.repo

        imageLoader.load(userImageView, repo.owner?.imageUrl ?: "")
        userTextView.text = repo.owner?.name
        starsTextView.text = repo.starCount.toString()
        issuesTextView.text = repo.issuesCount.toString()

    }
}
