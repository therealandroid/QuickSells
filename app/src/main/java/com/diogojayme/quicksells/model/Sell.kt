package com.diogojayme.quicksells.model

import java.util.*

data class Sell(var id: String, var price: Float = 0.2F, var costPrice: Float = 0.2F, var profit: Float = 0.2F, var date: Date)