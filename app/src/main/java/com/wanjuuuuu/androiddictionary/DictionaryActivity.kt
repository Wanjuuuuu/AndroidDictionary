package com.wanjuuuuu.androiddictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wanjuuuuu.androiddictionary.databinding.ActivityDictionaryBinding

class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDictionaryBinding>(
            this,
            R.layout.activity_dictionary
        )
    }
}