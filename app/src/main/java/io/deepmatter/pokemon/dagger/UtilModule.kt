package io.deepmatter.pokemon.dagger

import dagger.Module
import dagger.Provides
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.util.random.RandomiserImpl

@Module
class UtilModule {

    @Provides
    fun provideRandomiser(): Randomiser = RandomiserImpl()
}