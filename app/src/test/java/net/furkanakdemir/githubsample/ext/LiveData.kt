package net.furkanakdemir.githubsample.ext

import androidx.lifecycle.LiveData
import net.furkanakdemir.githubsample.TestObserver


fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}