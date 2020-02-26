package io.deepmatter.pokemon.dagger

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.deepmatter.pokemon.R
import io.deepmatter.pokemon.api.CardApi
import io.deepmatter.pokemon.core.RoundFactory
import io.deepmatter.pokemon.core.RoundFactoryImpl
import io.deepmatter.pokemon.util.random.Randomiser
import io.deepmatter.pokemon.util.value.ValueImpl
import io.deepmatter.pokemon.viewmodel.GameViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class GameModule {

    @Provides
    @IntoMap
    @ViewModelKey(value = GameViewModel::class)
    fun provideGameViewModel(api: CardApi,
                             factory: RoundFactory,
                             context: Context): ViewModel =
        GameViewModel(
            api,
            factory,
            context.resources.getInteger(R.integer.score_limit).let(::ValueImpl),
            AndroidSchedulers.mainThread())

    @Provides
    fun provideRoundFactory(randomiser: Randomiser): RoundFactory =
        RoundFactoryImpl(
            randomiser)
}