package com.example.bitfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "EntryActivity"

class EntryActivity : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var hoursEditText: EditText
    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        // TODO: Find the views for the screen
        dateEditText = findViewById(R.id.date_editText)
        hoursEditText = findViewById(R.id.hours_editText)
        enterButton = findViewById(R.id.button)

        enterButton.setOnClickListener{
            val date = dateEditText.text.toString()
            val hours = hoursEditText.text.toString()

            if (date.isNotEmpty() && hours.isNotEmpty()) {
                // Create a new SleepEntry with the input data
                val newEntry = EntryEntity(date = date, hours = hours)

                // Save entry to the database using a coroutine
                CoroutineScope(Dispatchers.IO).launch {
                    (application as SleepApplication).db.entryDao().insertOne(newEntry)
                    // Finish activity to return to previous screen
                    finish()
                }
            } else {
                // Handle invalid input (show a message or error if needed)
                Toast.makeText(this, "Please enter a valid date and number of hours", Toast.LENGTH_SHORT).show()
            }
        }

    }
}