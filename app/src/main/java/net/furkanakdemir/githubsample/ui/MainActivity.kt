package net.furkanakdemir.githubsample.ui

import android.os.Bundle
import androidx.navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.ui.data.Repo
import timber.log.Timber

@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findNavController(R.id.nav_host_fragment)
            .addOnDestinationChangedListener { _, destination, arguments ->

                val x: Repo? = arguments?.getParcelable(KEY_DETAIL_ARGS)
                Timber.w(x.toString())

                val title = when (destination.id) {
                    R.id.repoListFragment -> {
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        TITLE_HOME
                    }
                    R.id.repoDetailFragment -> {
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        val repo: Repo? = arguments?.getParcelable(KEY_DETAIL_ARGS)
                        repo?.let { it.name } ?: TITLE_HOME
                    }
                    else -> TITLE_HOME
                }

                setTitle(title)
            }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    companion object {
        private const val TITLE_HOME = "Home"
        private const val KEY_DETAIL_ARGS = "repo"
    }
}
