package com.dariobrux.pokemon.app.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.other.extensions.getDominantColor
import com.dariobrux.pokemon.app.other.extensions.getIdFromUrl
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.extensions.animateBackgroundColor
import com.dariobrux.pokemon.app.other.extensions.animateCardBackgroundColor
import timber.log.Timber
import java.util.*


/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This is the adapter applied to the RecyclerView in the MainFragment.
 *
 */
class MainAdapter(private val context: Context, private val items: List<Pokemon>, private val listener: OnPokemonSelectedListener?) : RecyclerView.Adapter<MainAdapter.PostViewHolder>() {

    interface OnPokemonSelectedListener {
        fun onPokemonSelected(pokemon: Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val pokemon = items[position]

        holder.card.animate().scaleX(1f).scaleY(1f).setDuration(200).start()

        Glide.with(context.applicationContext).asBitmap().load(pokemon.urlPicture).diskCacheStrategy(DiskCacheStrategy.ALL).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                Timber.e("Image loading failed")
                return true
            }

            // When the bitmap is loaded, I get the dominant color of the image
            // and use it as background color.
            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                resource ?: return true
                resource.getDominantColor(ContextCompat.getColor(context, R.color.white)) { color ->
                    holder.card.animateCardBackgroundColor(Color.parseColor("#000000"), color)
                }
                return false
            }

        }).into(holder.img)

        holder.txtName.text = pokemon.name.capitalize(Locale.getDefault())
        holder.txtNumber.text = pokemon.num?.toString() ?: ""

        holder.card.setOnClickListener {
            listener?.onPokemonSelected(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView = itemView.findViewById(R.id.card)
        var img: ImageView = itemView.findViewById(R.id.img)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }
}