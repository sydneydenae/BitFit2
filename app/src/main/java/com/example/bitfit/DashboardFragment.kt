package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val averageTextView = view.findViewById<TextView>(R.id.averageTextView)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            // Collect the average hours from the Flow
            (requireActivity().application as SleepApplication).db.entryDao().getAverageHours().collect { avgHours ->
                // Switch to the main thread for UI updates
                withContext(Dispatchers.Main) {
                    val roundedAvgHours = String.format("%.2f", avgHours)
                    averageTextView.text = roundedAvgHours.toString()
                    Toast.makeText(requireContext(), avgHours.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Button links to another page
        val button = view?.findViewById<Button>(R.id.clearButton)

        button?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                // Access the EntryDao and call deleteAll()
                (requireActivity().application as SleepApplication).db.entryDao().deleteAll()
                // Use Dispatchers.Main to switch back to the main thread for UI updates
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "All entries deleted.", Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(requireContext(),"All entries deleted.", Toast.LENGTH_SHORT).show()

        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}