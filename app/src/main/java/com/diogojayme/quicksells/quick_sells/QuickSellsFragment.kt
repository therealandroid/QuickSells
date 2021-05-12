package com.diogojayme.quicksells.quick_sells

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diogojayme.quicksells.App
import com.diogojayme.quicksells.App.SellsList.MULTIPLIER
import com.diogojayme.quicksells.NavigationActivity
import com.diogojayme.quicksells.R
import com.diogojayme.quicksells.model.Sell
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_sells.*
import kotlinx.android.synthetic.main.fragment_quick_sells.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.NumberFormat
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
        setupToolbar()
        return inflater.inflate(R.layout.fragment_quick_sells, container, false)
    }

    fun showKeyboard(editText: EditText) {

        editText.requestFocus()
        editText.setSelection(editText.text.length)

        GlobalScope.launch {
            delay(200L)

            val inputMethodManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInputFromWindow(
                editText.applicationWindowToken,
                InputMethodManager.SHOW_IMPLICIT, 0
            )
        }
    }

    fun setupToolbar(){
        (requireActivity() as NavigationActivity).title = "Vendas rapidas"
        (requireActivity() as NavigationActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as NavigationActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }


    fun formatCurrency(number: Float): String{
        var COUNTRY = "BR"
        var LANGUAGE = "pt"
        return NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(number)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showKeyboard(price)

        price.textInputAsFlow()
            .onEach {
                if(it.toString().isNotEmpty()){
                    val totalPrice = it.toString().toFloat()
                    val _costPrice = ( totalPrice - (totalPrice / MULTIPLIER))
                    val _profit = (totalPrice - (totalPrice - (totalPrice / MULTIPLIER)))

                    costPrice.text = String.format("%.2f", _costPrice)
                    profit.text = String.format("%.2f", _profit)
                }else{
                    costPrice.text = "0"
                    profit.text = "0"
                }

            }
            .launchIn(lifecycleScope)

        finalizar.setOnClickListener {
            if(costPrice.text.toString().isEmpty()){
                Snackbar.make(requireView(), "Digite um valor de venda", Snackbar.LENGTH_SHORT).show()
            }else{

                var _costPrice = 0.0F
                var _profit = 0.0F
                var _price = 0.0F

                if(costPrice.text.toString().isNotEmpty()) {
                    _costPrice = costPrice.text.toString().replace(",", ".").toFloat()
                }

                if(profit.text.toString().isNotEmpty()) {
                    _profit = profit.text.toString().replace(",", ".").toFloat()
                }

                if(price.text.toString().isNotEmpty()) {
                    _price = price.text.toString().replace(",", ".").toFloat()
                }

                App.SellsList.data.add(
                    Sell(
                        UUID.randomUUID().toString(),
                        _price,
                        _costPrice,
                        _profit,
                        Date()
                    )
                )

                costPrice.text = ""
                profit.text = ""
                price.setText("", TextView.BufferType.EDITABLE)

                Snackbar.make(requireView(), "Nova venda efetuada com sucesso", Snackbar.LENGTH_SHORT).show()

            }

        }
    }

}