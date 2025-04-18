package com.example.trying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Link to the layout file

        // Find the start button by its ID
        val startButton: Button = findViewById(R.id.startButton)

        // Set an action to perform when the start button is clicked
        startButton.setOnClickListener {
            // Create an Intent to start the QuestionActivity
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent) // Start the new activity
            // finish() // Optional: Close MainActivity so user can't go back to it
        }
    }
}