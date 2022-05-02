package com.example.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dictionaryapp.feature_dictionary.presentation.WordInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WordInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.onSearch("android")
        }

        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when(event) {
                    is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                        Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.state.observe(this){

            findViewById<TextView>(R.id.output).text = it.wordInfoItem.toString()

        }


    }
}