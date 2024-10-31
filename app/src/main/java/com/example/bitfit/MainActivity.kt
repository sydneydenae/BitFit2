package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    // Create var for displayable sleep entries, the recyclerview, and binding var
    private val entries = mutableListOf<DisplayEntry>()
   // private val context = Context
    private lateinit var entriesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bind
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        // set up recyclerview on main activity screen
        entriesRecyclerView = findViewById(R.id.entries)
        val entryAdapter = EntryAdapter(this, entries)
        entriesRecyclerView.adapter = entryAdapter

        // set up dividers in the vertical layout
        entriesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            entriesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        // continuously check database for changes and display items
        lifecycleScope.launch {
            (application as SleepApplication).db.entryDao().getAll().collect { databaseList ->
                // Map each entity in the database to a displayable model if needed
                databaseList.map { entity ->
                    DisplayEntry(
                        entity.date,
                        entity.hours
                    )
                }.also { mappedList ->
                    entries.clear()
                    entries.addAll(mappedList)
                    entryAdapter.notifyDataSetChanged()
                }
            }
        }

        // Button links to another page
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            // intent to go to another page
            val intent = Intent(this@MainActivity, EntryActivity::class.java)
            this@MainActivity.startActivity(intent)

        }
    }
}