package io.deepmatter.pokemon.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.deepmatter.pokemon.R
import io.deepmatter.pokemon.model.Card
import kotlinx.android.synthetic.main.view_card.view.*

class CardViewHolder(view: View,
                     private val onClick: (Card) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(card: Card) {
        itemView.apply {
            Glide
                .with(context)
                .load(card.image)
                .into(image)

            image.setOnClickListener { onClick.invoke(card) }
        }
    }

    companion object {

        @JvmStatic
        val layout = R.layout.view_card
    }
}