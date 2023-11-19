package com.example.lab_intents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.lab_intents.viewModels.FirstLabViewModel

class ExplicitActivity : AppCompatActivity() {

    private lateinit var btnSubmit : Button
    private lateinit var btnBack : Button
    private lateinit var userInput : EditText
    private lateinit var viewModel : FirstLabViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit)

        btnBack = findViewById(R.id.btnGoBack)
        btnSubmit = findViewById(R.id.btnSubmit)
        userInput = findViewById(R.id.editTextInput)
        viewModel = ViewModelProvider(this)[FirstLabViewModel::class.java]

        val bundle: Bundle? = intent.extras
        val input: String? = bundle?.getString("userInput") ?: "Click the buttons"
        viewModel.setExplicitInput(input.toString())

        btnSubmit.setOnClickListener {
            Intent().let { i ->
                i.putExtra("userInput",userInput.text.toString())
                setResult(Activity.RESULT_OK, i)
                finish() // mozime da pocnime nov intent so startActivity() i da go zgolemime stackot ili so finish da zavrsi ovoj activity i da se vratime na main.
            }
        }

        btnBack.setOnClickListener {
            Intent().let {i ->
                setResult(Activity.RESULT_CANCELED, i)
                finish()
            }
        }

    }
}