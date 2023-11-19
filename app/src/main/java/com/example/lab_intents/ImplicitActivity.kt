package com.example.lab_intents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ImplicitActivity : AppCompatActivity() {

    private lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit)

        textView = findViewById(R.id.listOfActivities)

        textView.text = getRegisteredActivities().joinToString("\n")

    }

    private fun getRegisteredActivities(): List<String> {
        val activitiesList = mutableListOf<String>()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val activities = packageManager.queryIntentActivities(intent, 0)

        for (resolveInfo in activities) {
            activitiesList.add(resolveInfo.activityInfo.name)
        }
        // Poradi toa sto imam xiaomi phone, mi javuve mnogu poveke activities vo spec na MUI za razlika od drugite kolegi.
        return activitiesList
    }
}