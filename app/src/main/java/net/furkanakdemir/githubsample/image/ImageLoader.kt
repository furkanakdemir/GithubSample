package net.furkanakdemir.githubsample.image

import android.widget.ImageView

interface ImageLoader {

    fun load(imageView: ImageView, imageUrl: String)
}
