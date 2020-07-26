package com.wanjuuuuu.androiddictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wanjuuuuu.androiddictionary.databinding.ActivityDictionaryBinding
import com.wanjuuuuu.androiddictionary.fragments.TermListFragment

class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDictionaryBinding>(
            this,
            R.layout.activity_dictionary
        )

        if (savedInstanceState == null) {
            val termListFragment = TermListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.dictionary_container, termListFragment).commit()
        }
    }
}