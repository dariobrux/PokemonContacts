package com.dariobrux.pokemon.app.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dariobrux.pokemon.app.R
import com.dariobrux.pokemon.app.other.PreferenceKeys
import com.dariobrux.pokemon.app.other.extensions.readValue
import com.dariobrux.pokemon.app.other.extensions.storeValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 *
 * Created by Dario Bruzzese on 22/10/2020.
 *
 * This is the main activity, where the application starts its
 * navigation.
 *
 * It is annotated by AndroidEntryPoint to integrate Hilt in this
 * activity.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    /**
     * Current items visualization. This is a public field
     * because it must be visible from the MainFragment.
     */
    @Inject
    lateinit var visualization: MutableLiveData<Visualization>

    /**
     * The list sorting. This is a public field
     * because it must be visible from the MainFragment.
     */
    @Inject
    lateinit var sorting: MutableLiveData<Sorting>

    /**
     * Register the permissions callback, which handles the user's response to the
     * system permissions dialog. Save the return value, an instance of
     * ActivityResultLauncher. You can use either a val, as shown in this snippet,
     * or a lateinit var in your onAttach() or onCreate() method.
     */
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

    /**
     * Current theme
     */
    private var theme = Theme.UNDEFINED
        set(value) {
            val isNight = when (value) {
                Theme.NIGHT_MODE_YES -> {
                    true
                }
                else -> {
                    false
                }
            }
            refreshUiNightMode(isNight)
            storeNightMode(isNight)
            field = value
        }

    enum class Theme {
        NIGHT_MODE_NO,
        NIGHT_MODE_YES,
        UNDEFINED;

        /**
         * Invert theme.
         * Night Mode -> Day Mode
         * Day Mode | Undefined -> Night Mode
         */
        fun inverse(): Theme {
            return when (this) {
                NIGHT_MODE_YES -> {
                    NIGHT_MODE_NO
                }
                else -> {
                    NIGHT_MODE_YES
                }
            }
        }
    }

    enum class Visualization {
        LIST,
        GRID
    }

    enum class Sorting {
        AZ,
        NUM
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Check contacts permission
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
//                showInContextUI(...)
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
//                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_CODE)
            }
        }

        // Check what is the current theme
        readNightMode {

            // Enter here if theme has not already set.
            when ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    Timber.tag(TAG).d("Night mode is not active, we're in day time")
                    theme = Theme.NIGHT_MODE_NO
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    Timber.tag(TAG).d("Night mode is active, we're at night")
                    theme = Theme.NIGHT_MODE_YES
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    Timber.tag(TAG).d("We don't know what mode we're in, assume notnight")
                    theme = Theme.UNDEFINED
                }
            }
        }

        // Add a button on the toolbar
        toolbar?.let {
            it.inflateMenu(R.menu.menu)
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_theme -> {
                        // Switch theme
                        theme = theme.inverse()
                    }
                }
                false
            }
        }

        // Add the bottom bar item listener to change the items visualization.
        bottomBarVisualization?.onItemSelectedListener = { _, menuItem ->
            when (menuItem.itemId) {
                R.id.list -> {
                    visualization.value = Visualization.LIST
                }
                R.id.grid -> {
                    visualization.value = Visualization.GRID
                }
            }
        }

        // Add the bottom bar item listener to change the items visualization.
        bottomBarSort?.onItemSelectedListener = { _, menuItem ->
            when (menuItem.itemId) {
                R.id.sortAZ -> {
                    sorting.value = Sorting.AZ
                }
                R.id.sortNum -> {
                    sorting.value = Sorting.NUM
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    /**
     * Read the night mode from the DataStore.
     * Restore the night theme if in the DataStore is stored night.
     * Restore the day theme if in the DataStore is stored day.
     */
    private fun readNightMode(defaultFunc: () -> Unit) {
        lifecycleScope.launch {
            dataStore.readValue(PreferenceKeys.THEME_NIGHT, {
                theme = if (this) {
                    Theme.NIGHT_MODE_YES
                } else {
                    Theme.NIGHT_MODE_NO
                }
            }, {
                defaultFunc.invoke()
            })
        }
    }

    /**
     * Store the night mode into the DataStore
     * @param isNight true if is in night mode, false otherwise.
     */
    private fun storeNightMode(isNight: Boolean) {
        lifecycleScope.launch {
            dataStore.storeValue(PreferenceKeys.THEME_NIGHT, isNight)
        }
    }

    /**
     * Change the colors of the ui when the app is in day/dark mode.
     */
    private fun refreshUiNightMode(isNight: Boolean) {
        val color = if (isNight) {
            ContextCompat.getColor(this, R.color.dark) to ContextCompat.getColor(
                this,
                R.color.black
            )
        } else {
            ContextCompat.getColor(this, R.color.day) to ContextCompat.getColor(this, R.color.white)
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color.first
        window.navigationBarColor = color.first
        toolbar?.setBackgroundColor(color.first)
        bottomBarVisualization?.setBackgroundColor(color.first)
        bottomBarSort?.setBackgroundColor(color.first)
        mainContainerRoot?.setBackgroundColor(color.second)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUEST_CODE = 1234
    }
}