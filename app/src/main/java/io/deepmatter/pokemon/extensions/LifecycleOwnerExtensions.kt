package io.deepmatter.pokemon.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import io.deepmatter.pokemon.util.event.Event

fun <T : Any?> LifecycleOwner.observeModel(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this::getLifecycle, observer)
}

inline fun LifecycleOwner.observeEvent(liveData: LiveData<Event<Unit>>,
                                       crossinline observer: () -> Unit) {
    liveData.observe(this::getLifecycle) { event ->
        event.getContentIfNotHandled()
            ?.let {
                observer.invoke()
            }
    }
}