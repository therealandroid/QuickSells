package com.diogojayme.quicksells

import android.app.Application
import com.diogojayme.quicksells.model.Sell

class App: Application() {

    object SellsList {
        val data = mutableListOf<Sell>()
        val MULTIPLIER = 3
    }

    override fun onCreate() {
        super.onCreate()
    }

}