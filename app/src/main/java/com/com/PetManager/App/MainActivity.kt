package com.kroger.classapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.kroger.classapp.databinding.ActivityMainBinding
import com.kroger.classapp.ui.PetCharacterListFragment
import com.kroger.classapp.ui.PetNoteFragment
import com.kroger.classapp.ui.PetPictureFragment
import com.kroger.classapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Example Channel" // The user-visible name of the channel.
            val descriptionText = "Example Channel Description" // The user-visible description of the channel.
            val importance = NotificationManager.IMPORTANCE_DEFAULT // Specify the importance level
            val channel = NotificationChannel("channel1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashScreen.value
            }
        }*/

        // Initialize ActionBarDrawerToggle, which will control the toggle of the drawer.
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open_nav, R.string.close_nav)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Enable the button to open or close the drawer.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the default fragment when the app starts.
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, PetCharacterListFragment())
        }

        // Set the click listener for the items in the drawer.
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // Replace the fragment when item1 is selected.
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, PetCharacterListFragment())
                    }
                }
                R.id.nav_image -> {

                    supportFragmentManager.commit{
                        replace(R.id.fragment_container_view, PetPictureFragment())
                    }
                }

                R.id.nav_share ->{
                    supportFragmentManager.commit{
                        replace(R.id.fragment_container_view, PetNoteFragment())
                    }
                }
                R.id.nav_notification -> supportFragmentManager.commit {
                    replace(R.id.fragment_container_view, NotificationFragment())
                }
            }
            // Close the drawer after an item is selected.
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // This will make the drawer open or close when the button is selected.
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
