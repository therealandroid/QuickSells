package com.diogojayme.quicksells.quick_sells

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diogojayme.quicksells.App
import com.diogojayme.quicksells.R
import com.diogojayme.quicksells.model.Sell
import kotlinx.android.synthetic.main.fragment_quick_sells.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*


@ExperimentalCoroutinesApi
fun EditText.textInputAsFlow() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { textInput: CharSequence?, _, _, _ ->
        offer(textInput)
    }

    awaitClose { this@textInputAsFlow.removeTextChangedListener(watcher) }
}


class QuickSellsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quick_sells, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        price.textInputAsFlow()
            .debounce(100)
            .onEach {
                custom.text = "" + (it.toString().toFloat() - (it.toString().toFloat() / 3))
                lucro.text = "" + (it.toString().toFloat() - (it.toString().toFloat() - (it.toString().toFloat() / 3)))
                println(it.toString().toInt())
            }
            .launchIn(lifecycleScope)

        finalizar.setOnClickListener {
            var costPrice = custom.text.toString().toFloat()
            var profit = lucro.text.toString().toFloat()
            var price = price.text.toString().toFloat()

            App.SellsList.data.add(Sell(UUID.randomUUID().toString(), price, costPrice, profit, System.currentTimeMillis()))
        }
    }

}