package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val TAG = "GameViewModel"

class GameViewModel : ViewModel() {

    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount : LiveData<Int> get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambleWord: LiveData<String>
        get() = _currentScrambledWord

    private var wordsList : MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d(TAG, "GameViewModel created")
        getNextWord()
    }


//    override fun onCleared() {
//        Log.d(TAG, "onCleared: GameViewModel destroyed")
//        super.onCleared()
//    }

    private fun getNextWord() {
        currentWord = allWordsList.random()

        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)){
            getNextWord()
        }
        else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }

    }

    fun nextWord(): Boolean {
        return if ((currentWordCount.value)!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }

    fun isUserWordCorrect(playerWord: String) : Boolean {
        return if(playerWord.equals(currentWord, false)){
            increaseScore()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun reinitializeData () {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }




}