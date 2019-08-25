# GithubSample

This is a sample showing a list of repositories on Github by searching username. 

You can find a debug apk in `/apk` directory


### Android Development
GithubSample attempts to use the latest libraries and tools:

  - Written in [Kotlin](https://kotlinlang.org/)
  - Built with [MVVM Architectural style](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) recommended by Google
  - Used [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for asynchronous works
  - Used [Architecture Components](https://developer.android.com/topic/libraries/architecture/): LiveData and Lifecycle-components, Navigation
  - Used [dagger-android](https://google.github.io/dagger/android.html) for Dependency Injection
  - Used [Glide](https://bumptech.github.io/glide/) for image loading and caching
  - Used [Retrofit](https://square.github.io/retrofit/) for network requests

### Code Style

This project uses the following tools to maintaing code quality. The configurations can be found in `/qa` directory

- [ktlint](https://ktlint.github.io/)
- [detekt](https://arturbosch.github.io/detekt/)
- [Android Lint](http://tools.android.com/tips/lint)

  ```
  ./gradlew lint detekt ktlintCheck
  ``` 
  
  
