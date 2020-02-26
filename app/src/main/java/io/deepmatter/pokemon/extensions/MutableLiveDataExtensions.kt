package io.deepmatter.pokemon.extensions

import androidx.lifecycle.MutableLiveData
import io.deepmatter.pokemon.util.event.Event

fun MutableLiveData<Event<Unit>>.perform() {
    value = Event(Unit)
}