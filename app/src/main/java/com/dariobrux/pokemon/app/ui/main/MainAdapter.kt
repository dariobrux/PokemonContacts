package com.dariobrux.pokemon.app.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
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
import com.dariobrux.pokemon.app.data.models.ContactData
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.extensions.animateCardBackgroundColor
import com.dariobrux.pokemon.app.other.extensions.getDominantColor
import timber.log.Timber
import java.util.*


/**
 *
 * Created by Dario Bruzzese on 20/10/2020.
 *
 * This is the adapter applied to the RecyclerView in the MainFragment.
 *
 */
class MainAdapter(private val context: Context, private val items: List<Any>, private val listener: OnItemSelectedListener?) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnItemSelectedListener {

        /**
         * Invoke when a pokemon is selected.
         * @param pokemon the pokemon selected.
         */
        fun onPokemonSelected(pokemon: Pokemon)

        /**
         * Invoke when a contact is selected.
         * @param contact the contact selected.
         */
        fun onContactSelected(contact: ContactData)
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is Pokemon) {
            return POKEMON
        } else if (items[position] is ContactData) {
            return CONTACT
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            POKEMON -> {
                configurePokemon(holder, items[position] as Pokemon)
            }
            else -> {
                configureContact(holder, items[position] as ContactData)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Set the pokemon layout showing the picture, the name and the id.
     * @param holder the ViewHolder.
     * @param pokemon the item in the list at current position.
     */
    private fun configurePokemon(holder: ViewHolder, pokemon: Pokemon) {

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
            listener?.onPokemonSelected(pokemon)
        }
    }

    /**
     * Set the contact layout showing the image, the phone number and the display name.
     * @param holder the ViewHolder.
     * @param contact the item in the list at current position.
     */
    private fun configureContact(holder: ViewHolder, contact: ContactData) {

        holder.card.animate().scaleX(1f).scaleY(1f).setDuration(200).start()

        contact.picture?.let {
            Glide.with(context.applicationContext).asBitmap().fitCenter().load(Uri.parse(it)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img)
        }

        holder.txtName.text = contact.phoneNumbers?.firstOrNull() ?: ""
        holder.txtNumber.text = contact.displayName ?: ""

        holder.card.setOnClickListener {
            listener?.onContactSelected(contact)
        }
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView = itemView.findViewById(R.id.card)
        var img: ImageView = itemView.findViewById(R.id.img)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }

    companion object {
        private const val POKEMON = 0
        private const val CONTACT = 1
    }
}