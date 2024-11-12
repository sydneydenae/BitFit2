package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val entries = mutableListOf<DisplayEntry>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        val fragmentManager: FragmentManager = supportFragmentManager
        // define  fragments
        val logFragment: Fragment = LogFragment()
        val dashboardFragment: Fragment = DashboardFragment()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_log -> fragment = logFragment
                R.id.action_dashboard -> fragment = dashboardFragment
            }
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
            true
        }
        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_log
        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(LogFragment())

        // Button links to another page
        val button = view.findViewById<Button>(R.id.add_entry_button)

        button.setOnClickListener {
            // intent to go to another page
            val intent = Intent(this@MainActivity, EntryActivity::class.java)
            this@MainActivity.startActivity(intent)
        }


    }
    // func to swap fragment
    private fun replaceFragment(logFragment: LogFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, logFragment)
        fragmentTransaction.commit()
    }
}