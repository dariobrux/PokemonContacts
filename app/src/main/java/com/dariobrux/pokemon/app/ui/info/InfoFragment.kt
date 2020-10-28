package com.dariobrux.pokemon.app.ui.info

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.data.models.ContactData
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber
import java.io.File
import java.util.*

/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 */

@AndroidEntryPoint
class InfoFragment : Fragment() {

    /**
     * The pokemon object
     */
    private var pokemon: Pokemon? = null

    /**
     * The contact object
     */
    private var contact: ContactData? = null

    /**
     * The ViewModel
     */
    private val viewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = requireArguments().getSerializable("pokemon") as? Pokemon
        contact = requireArguments().getSerializable("contact") as? ContactData
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when {
            pokemon != null -> {
                showPokemonData()
            }
            contact != null -> {
                showContactData()
            }
        }
    }

    /**
     * Show the info of the contact.
     */
    private fun showContactData() {
        containerRoot?.setBackgroundResource(R.color.gray)
        if (contact!!.picture == null || contact!!.picture?.isEmpty() == true) {
            thumb?.toGone()
        } else {
            val photoUri: Uri = Uri.fromFile(File(contact!!.picture!!))
            Glide.with(requireContext()).load(photoUri).into(thumb)
            card?.animate()?.scaleX(1f)?.scaleY(1f)?.setDuration(200)?.start()
        }
        txtName?.text = contact!!.displayName?.capitalize(Locale.getDefault()) ?: ""
        if (contact!!.phoneNumbers == null || contact!!.phoneNumbers?.isEmpty() == true) {
            txtExperience?.toGone()
        } else {
            txtExperience?.text = contact!!.phoneNumbers?.joinToString(separator = "\n") ?: ""
        }
        txtHeight?.toGone()
        txtWeight?.toGone()
    }

    /**
     * Show the info of the pokemon.
     * Observe the ViewModel LiveData property to get the info of the
     * pokemon and refresh the layout.
     */
    private fun showPokemonData() {
        txtName?.text = pokemon!!.name.capitalize(Locale.getDefault())
        viewModel.getPokemonData(pokemon!!.name, pokemon!!.url ?: "").observe(this.viewLifecycleOwner) {
            val pokemonData = it.data ?: return@observe
            txtExperience?.text = getString(R.string.base_experience, pokemonData.baseExperience)
            txtHeight?.text = getString(R.string.height, pokemonData.height)
            txtWeight?.text = getString(R.string.weight, pokemonData.weight)

            Glide.with(requireContext()).asBitmap().load(pokemon!!.urlPicture).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    Timber.e("Image loading failed")
                    return true
                }

                // When the bitmap is loaded, I get the dominant color of the image
                // and use it as background color.
                override fun onResourceReady(bitmap: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    bitmap ?: return true
                    bitmap.getDominantColor(ContextCompat.getColor(requireContext(), R.color.white)) { color ->
                        card?.setCardBackgroundColor(color)
                        val startColor = requireActivity().toMainActivity()?.mainContainerRoot?.background?.toColorDrawable()?.color ?: return@getDominantColor
                        containerRoot?.animateBackgroundColor(startColor, color.changeAlpha(190))
                        card?.animate()?.scaleX(1f)?.scaleY(1f)?.setDuration(200)?.start()
                    }
                    return false
                }

            }).into(thumb)
        }
    }
}