package io.deepmatter.pokemon.dagger

import dagger.Component
import io.deepmatter.pokemon.view.GameActivity

@Component(modules = [
    ApiModule::class,
    AppModule::class,
    GameModule::class,
    UtilModule::class])
interface AppComponent {

    fun inject(activity: GameActivity)
}