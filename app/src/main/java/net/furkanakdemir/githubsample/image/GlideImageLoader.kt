package net.furkanakdemir.githubsample.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import net.furkanakdemir.githubsample.R
import javax.inject.Inject

class GlideImageLoader @Inject
constructor(private val context: Context) : ImageLoader {

    override fun load(imageView: ImageView, imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_code)
            .error(R.drawable.ic_code)
            .into(imageView)
    }
}
