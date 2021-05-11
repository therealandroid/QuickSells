package com.diogojayme.quicksells.sells_list

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.diogojayme.quicksells.App
import com.diogojayme.quicksells.R
import com.diogojayme.quicksells.login.LoginFragmentDirections
import kotlinx.android.synthetic.main.fragment_list_sells.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class SellsListFragment : Fragment() {

    val adapter = SellsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sells, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vender.setOnClickListener {
            val directions = SellsListFragmentDirections.toQuickSell()
            Navigation.findNavController(it).navigate(directions)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        adapter.submitData(App.SellsList.data)
        adapter.notifyDataSetChanged()
    }

}