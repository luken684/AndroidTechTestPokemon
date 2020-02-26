package io.deepmatter.pokemon.util.random

import kotlin.random.Random

class RandomiserImpl : Randomiser {

    override fun getIntInRange(startInclusive: Int,
                               endInclusive: Int): Int = Random
        .Default
        .nextInt(startInclusive, endInclusive)
}