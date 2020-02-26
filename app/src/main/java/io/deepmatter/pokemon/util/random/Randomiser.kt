package io.deepmatter.pokemon.util.random

interface Randomiser {

    fun getIntInRange(startInclusive: Int, endInclusive: Int): Int
}