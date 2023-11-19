package com.example.lab_intents.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstLabViewModel : ViewModel() {

    private val _explicitInput : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun returnInputValue() : String {
        return this._explicitInput.value ?: "Click the buttons"
    }

    fun returnLiveInputValue() : MutableLiveData<String> {
        return _explicitInput
    }

    fun setExplicitInput(input: String) {
        if (input == "") {
            this._explicitInput.value = "Click the buttons"
        } else {
            this._explicitInput.value = input
        }
    }
}