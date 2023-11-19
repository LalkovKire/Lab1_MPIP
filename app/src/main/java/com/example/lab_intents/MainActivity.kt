package com.example.lab_intents

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.lab_intents.viewModels.FirstLabViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnExplicit : Button
    private lateinit var btnImplicit : Button
    private lateinit var textView : TextView
    private lateinit var viewModel : FirstLabViewModel
    private lateinit var btnImageSelect : Button

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val resultString = data?.getStringExtra("userInput")

                if (resultString != null) {
                    viewModel.setExplicitInput(resultString)
                }
            }
        }
    private val imageSelectionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            selectedImageUri?.let { uri ->
                val openImageIntent = Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, "image/*")
                openImageIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(openImageIntent)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnExplicit = findViewById(R.id.btnGoToExplicitActivity)
        btnImplicit = findViewById(R.id.btnGoToImplicitActivity)
        textView = findViewById(R.id.mainActivityTextView)
        btnImageSelect = findViewById(R.id.btnSelectImage)
        viewModel = ViewModelProvider(this)[FirstLabViewModel::class.java]

        viewModel.returnLiveInputValue().observe(this) {
            textView.text = viewModel.returnInputValue()
        }

        btnExplicit.setOnClickListener {
            Intent(this, ExplicitActivity::class.java).let { intent ->
                intent.putExtra("userInput", viewModel.returnInputValue())
                activityResultLauncher.launch(intent)
            }
        }

        btnImplicit.setOnClickListener {
            Intent().apply {
                action = "mk.ukim.finki.mpip.IMPLICIT_ACTION"
                type = "text/plain"
            }.let {
                i -> startActivity(i)
            }
        }

        btnImageSelect.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            imageSelectionLauncher.launch(i)
        }

    }
}