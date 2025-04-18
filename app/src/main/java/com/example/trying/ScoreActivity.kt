package com.example.trying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess // For exiting the app

class ScoreActivity : AppCompatActivity() {

    // Data passed from QuestionActivity (for review)
    private lateinit var questions: Array<String>
    private lateinit var answers: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // --- Get UI Elements ---
        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val scoreFeedbackTextView: TextView = findViewById(R.id.scoreFeedbackTextView)
        val reviewButton: Button = findViewById(R.id.reviewButton)
        val exitButton: Button = findViewById(R.id.exitButton)

        // --- Retrieve data from Intent ---
        val score = intent.getIntExtra("EXTRA_SCORE", 0)
        val totalQuestions = intent.getIntExtra("EXTRA_TOTAL_QUESTIONS", 5)
        // Retrieve arrays for review - handle potential null case though unlikely here
        questions = intent.getStringArrayExtra("EXTRA_QUESTIONS") ?: arrayOf()
        answers = intent.getBooleanArrayExtra("EXTRA_ANSWERS") ?: booleanArrayOf()


        // --- Display Score ---
        scoreTextView.text = "Your Score: $score / $totalQuestions"

        // --- Display Feedback based on score ---
        val feedback = when {
            score >= 3 -> "Great job! You know your history."
            score > 0 -> "Keep practising! You'll get there."
            else -> "Looks like a review is needed. Keep learning!"
        }
        scoreFeedbackTextView.text = feedback

        // --- Set Button Click Listeners ---
        reviewButton.setOnClickListener {
            // Start ReviewActivity, passing the questions and answers
            val reviewIntent = Intent(this, ReviewActivity::class.java)
            reviewIntent.putExtra("EXTRA_QUESTIONS", questions)
            reviewIntent.putExtra("EXTRA_ANSWERS", answers)
            startActivity(reviewIntent)
        }

        exitButton.setOnClickListener {
            // Terminate the application completely
            finishAffinity() // Closes all activities in the task
            exitProcess(0) // Exits the JVM process
        }
    }
}