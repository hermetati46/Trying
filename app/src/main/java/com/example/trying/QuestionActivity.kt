package com.example.trying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat // Needed for getting colors

class QuestionActivity : AppCompatActivity() {

    // --- UI Elements ---
    private lateinit var questionNumberTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var nextButton: Button

    // --- Quiz Data ---
    // Parallel arrays for questions and answers
    private val questions = arrayOf(
        "Nelson Mandela became president of South Africa in 1994.",
        "The Great Wall of China is visible from the Moon with the naked eye.",
        "The French Revolution began in 1789.",
        "Cleopatra VII was of Greek descent, not Egyptian.",
        "World War I ended in 1917."
    )
    private val answers = booleanArrayOf(
        true,  // Mandela president in 1994
        false, // Great Wall not visible from Moon
        true,  // French Revolution start year
        true,  // Cleopatra's descent
        false  // WWI ended in 1918
    )

    // --- Quiz State ---
    private var currentQuestionIndex = 0
    private var score = 0
    private var answerGiven = false // Track if user answered the current question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // --- Initialize UI Elements ---
        questionNumberTextView = findViewById(R.id.questionNumberTextView)
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)
        nextButton = findViewById(R.id.nextButton)

        // --- Load the first question ---
        loadQuestion()

        // --- Set Button Click Listeners ---
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            moveToNextQuestion()
        }
    }

    // --- Function to load the current question ---
    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val questionNumber = currentQuestionIndex + 1
            questionNumberTextView.text = "Question $questionNumber/${questions.size}"
            questionTextView.text = questions[currentQuestionIndex]

            // Reset for the new question
            feedbackTextView.text = "" // Clear previous feedback
            trueButton.isEnabled = true  // Enable answer buttons
            falseButton.isEnabled = true
            nextButton.isEnabled = false // Disable next until answer is given
            answerGiven = false
        } else {
            // All questions answered, go to Score screen
            goToScoreScreen()
        }
    }

    // --- Function to check the user's answer ---
    private fun checkAnswer(userAnswer: Boolean) {
        if (answerGiven) return // Prevent multiple answers for the same question

        answerGiven = true
        trueButton.isEnabled = false // Disable answer buttons after selection
        falseButton.isEnabled = false
        nextButton.isEnabled = true   // Enable the next button

        val correctAnswer = answers[currentQuestionIndex]

        if (userAnswer == correctAnswer) {
            // Correct Answer
            score++
            feedbackTextView.text = "Correct!"
            feedbackTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        } else {
            // Incorrect Answer
            feedbackTextView.text = "Incorrect. The answer was $correctAnswer."
            feedbackTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        }
    }

    // --- Function to move to the next question ---
    private fun moveToNextQuestion() {
        currentQuestionIndex++
        loadQuestion() // Load the next question or finish
    }

    // --- Function to navigate to the Score Activity ---
    private fun goToScoreScreen() {
        val intent = Intent(this, ScoreActivity::class.java)
        // Pass the final score and total questions to ScoreActivity
        intent.putExtra("EXTRA_SCORE", score)
        intent.putExtra("EXTRA_TOTAL_QUESTIONS", questions.size)
        // Pass questions and answers for the review screen
        intent.putExtra("EXTRA_QUESTIONS", questions)
        intent.putExtra("EXTRA_ANSWERS", answers)
        startActivity(intent)
        finish() // Close QuestionActivity
    }
}