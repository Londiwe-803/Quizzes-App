package vcmsa.londiwe.exampple.quizzesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//end of viewCompat
        //code goes here
        //link the elements from the GUI to the background
        val tvQuizQuestions = findViewById<TextView>(R.id.tvQuizQuestions)
        val rbtngAnswers = findViewById<RadioGroup>(R.id.rbtngAnswers)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val username = intent.getStringExtra("username")
        //Array of questions and answers
        val questions = arrayOf(
            "Thabo Mbeki was the first black president",
            "The apartheid regime ended in 1994.",
            "The ANC has been the most dominant political party since 1994.",
            "Durban is the capital of South Africa.",
            "The OR Tambo international airport was named after Oliver Tambo."
        )
        val answers = arrayOf(
            arrayOf("A: True", "B: False"),
            arrayOf("A: True", "B: False"),
            arrayOf("A: True", "B: False"),
            arrayOf("A: True", "B: False"),
            arrayOf("A: True", "B: False")
        )
        var userAnswers = arrayOfNulls<String>(5)
        val correctAnswers = arrayOf(
            "B: False",
            "A: True",
            "A: True",
            "B: False",
            "A: True"
        )
        var counter = 0
        //code logic
        //set the first question before waiting for user interaction
        tvQuizQuestions.text = questions[counter]
        for (i in 0 until rbtngAnswers.childCount) {
            val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
            radioButton.text = answers[counter][i]
        }
        btnNext.setOnClickListener {
            if (counter < 5) {
                var selectedOption = rbtngAnswers.checkedRadioButtonId

                if (selectedOption != -1) {
                  val selectedRbtn = findViewById<RadioButton>(selectedOption)
                    userAnswers[counter] = selectedRbtn.text.toString()
                    counter++
                } else {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Stops execution if no answer is selected
                }

                if (counter < 5){
                    tvQuizQuestions.text = questions[counter]
                    for (i in 0 until rbtngAnswers.childCount) {
                        val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
                        radioButton.text = answers[counter][i]
                    }
                    rbtngAnswers.clearCheck()
                } else {
                    val intent = Intent(this, ResultsActivity::class.java)
                    var score = 0

                    // Calculate score
                    for (i in userAnswers.indices) {
                        if (userAnswers[i] == correctAnswers[i]) {
                            score++
                        }
                    }

                    intent.putExtra("score", score)
                    intent.putExtra("username", username)

                    startActivity(intent)
                    finish()
                }
            }
        }
    }//emd of onCreate
}//end of QuizActivity2