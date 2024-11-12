package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class LogFragment : Fragment() {
    // Create var for displayable sleep entries, the recyclerview, and binding var
    private val entries = mutableListOf<DisplayEntry>()
    // private val context = Context
    private lateinit var entriesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_log, container, false)
        // set up recyclerview on main activity screen
        entriesRecyclerView = view.findViewById(R.id.entries)
        val entryAdapter = EntryAdapter(view.context, entries)
        entriesRecyclerView.adapter = entryAdapter

        // set up dividers in the vertical layout
        entriesRecyclerView.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            entriesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        // continuously check database for changes and display items
        lifecycleScope.launch {
            (requireActivity().application as SleepApplication).db.entryDao().getAll().collect { databaseList ->
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
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): LogFragment {
            return LogFragment()
        }
    }
}