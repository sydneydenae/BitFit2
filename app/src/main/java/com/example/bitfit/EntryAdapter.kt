package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "EntryAdapter"

class EntryAdapter(private val context: Context, private val entries: List<DisplayEntry>) :
    RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount() = entries.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val displayDateTextView = itemView.findViewById<TextView>(R.id.display_date_textView)
        private val displayHoursTextView = itemView.findViewById<TextView>(R.id.display_hours_textView)

        init {
            itemView.setOnClickListener(this)
        }

        // helper method to help set up the onBindViewHolder method
        fun bind(entry: DisplayEntry) {
            displayDateTextView.text = entry.date
            displayHoursTextView.text = entry.hours
        }

        /* use this for oon button click
        // TODO: Navigate to Details screen and pass selected article
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(ARTICLE_EXTRA, article)
        context.startActivity(intent)
        */
        override fun onClick(v: View?) {
            // TODO: Get selected article

        }
    }
}


