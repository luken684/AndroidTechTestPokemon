package io.deepmatter.pokemon.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.deepmatter.pokemon.model.Card

class CardAdapter(private val onClick: (Card) -> Unit) : RecyclerView.Adapter<CardViewHolder>() {

    var items = emptyList<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        LayoutInflater
            .from(parent.context)
            .inflate(CardViewHolder.layout, parent, false)
            .let { CardViewHolder(it, onClick) }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = items[position]
        .let(holder::bind)
}