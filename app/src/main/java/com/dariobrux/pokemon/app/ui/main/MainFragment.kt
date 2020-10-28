package com.dariobrux.pokemon.app.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.data.models.ContactData
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.extensions.toMainActivity
import com.dariobrux.pokemon.app.ui.MainActivity
import com.dariobrux.pokemon.app.ui.utils.GridSpaceItemDecoration
import com.dariobrux.pokemon.app.ui.utils.LinearSpaceItemDecoration
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber


/**
 *
 * Created by Dario Bruzzese on 21/10/2020.
 *
 * This is the main fragment, where the layout shows
 * the list of all pokemon. It limits the items to display
 * each time. So, after having scrolled the list, an HTTP request must be
 * done to retrieve another group of pokemon.
 *
 * I declare this class with AndroidEntryPoint annotation, to tell the activity that
 * we will be injecting dependency here. Without this, the DI won't work.
 */

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnItemSelectedListener {

    /**
     * The ViewModel
     */
    private val viewModel: MainViewModel by viewModels()

    private var isContactPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.adapter == null) {
            viewModel.adapter = MainAdapter(requireContext(), viewModel.combinedItemsList, this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RxPermissions(this)
            .request(Manifest.permission.READ_CONTACTS)
            .subscribe { granted ->
                isContactPermissionGranted = granted
                // The permission is granted. This is always true for Android pre-M
                if (granted) {
                    initFragmentViews()
                } else {
                    // The permission is denied
                    initFragmentViews()
                }
            }
    }

    /**
     * Initialize the views of this fragment.
     */
    private fun initFragmentViews() {

        // Set the RecyclerView with its LayoutManager, ItemDecorator, Adapter and callbacks.
        recycler?.let {
            it.adapter = viewModel.adapter
        }

        // At this point, I must observe the ViewModel to get the updated list
        // of pokemon only if the current list is empty. I let do in this way
        // because, when I change the theme, the application is refreshed, and
        // I could incur in any bug.
        if (viewModel.combinedItemsList.isEmpty()) {
            getPokemonAndContactList()
        }

        // Observe the visualization to transform the list to grid and vice versa.
        requireActivity().toMainActivity()?.visualization?.observe(this.viewLifecycleOwner) { visualization ->
            visualization ?: return@observe
            recycler?.let { rec ->
                if (rec.itemDecorationCount != 0) rec.removeItemDecorationAt(0)
                if (rec.layoutManager as? GridLayoutManager == null) {
                    rec.layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
                }
            }

            when (visualization) {
                MainActivity.Visualization.LIST -> {
                    recycler?.let {
                        (it.layoutManager as GridLayoutManager).spanCount = 1
                        it.addItemDecoration(LinearSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
                    }
                }
                MainActivity.Visualization.GRID -> {
                    recycler?.let {
                        (it.layoutManager as GridLayoutManager).spanCount = 2
                        it.addItemDecoration(GridSpaceItemDecoration(requireContext().resources.getDimensionPixelSize(R.dimen.regular_space)))
                    }
                }
            }
            viewModel.adapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetOffset()
    }

    /**
     * Observe the ViewModel to get the list of the pokemon to show.
     */
    private fun getPokemonAndContactList() {

        // Get the contact list
        val contactList = if (isContactPermissionGranted) {
            viewModel.getContactList()
        } else {
            emptyList()
        }

        viewModel.getPokemonList()?.observe(this.viewLifecycleOwner) {
            Timber.d("Observer the dataInfo object. It contains ${it.data?.pokemonList?.size ?: 0} pokemon")
            val items = viewModel.getCombinedContactsAndPokemon(contactList, it.data?.pokemonList ?: emptyList())
            viewModel.combinedItemsList.addAll(items)

            // Refresh the adapter.
            viewModel.adapter?.notifyDataSetChanged()
        }
    }

    /**
     * Invoke when a pokemon is selected.
     * @param pokemon the pokemon selected.
     */
    override fun onPokemonSelected(pokemon: Pokemon) {
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_infoFragment, Bundle().apply {
            putSerializable("pokemon", pokemon)
        })
    }

    /**
     * Invoke when a contact is selected. The contact received will be converted to a ContactData.
     * @param contact the contact selected.
     */
    override fun onContactSelected(contact: ContactData) {
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_infoFragment, Bundle().apply {
            putSerializable("contact", contact)
        })
    }
}