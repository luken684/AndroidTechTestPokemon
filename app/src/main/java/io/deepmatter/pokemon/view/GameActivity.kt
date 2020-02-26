package io.deepmatter.pokemon.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.deepmatter.pokemon.R
import io.deepmatter.pokemon.dagger.AppModule
import io.deepmatter.pokemon.dagger.DaggerAppComponent
import io.deepmatter.pokemon.extensions.observeEvent
import io.deepmatter.pokemon.extensions.observeModel
import io.deepmatter.pokemon.viewmodel.GameViewModel
import io.deepmatter.pokemon.viewmodel.State
import kotlinx.android.synthetic.main.activity_game.*
import javax.inject.Inject

class GameActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var model: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)

        model = ViewModelProviders
            .of(this, factory)
            .get(GameViewModel::class.java)
            .apply(::setup)
    }

    override fun onStart() {
        super.onStart()

        model.start()
    }

    private fun setup(model: GameViewModel) {
        val adapter = CardAdapter(model::cardTapped)
        recycler.adapter = adapter

        observeModel(model.state, ::state)
        observeModel(model.roundCards) { adapter.items = it }

        observeEvent(model.indicateCorrectSelection)
            { Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show() }
        observeEvent(model.indicateIncorrectSelection)
            { Toast.makeText(this, R.string.wrong, Toast.LENGTH_SHORT).show() }

        retry.setOnClickListener { model.retryTapped() }
    }

    private fun state(state: State) {
        content.isVisible = state is State.Round
        victory.isVisible = state is State.Victory
        loading.isVisible = state is State.Loading
        error.isVisible = state is State.Error
    }
}