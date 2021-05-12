package com.diogojayme.quicksells.sells_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.diogojayme.quicksells.App
import com.diogojayme.quicksells.NavigationActivity
import com.diogojayme.quicksells.R
import kotlinx.android.synthetic.main.fragment_list_sells.*


class SellsListFragment : Fragment(), OnItemDeletedListener {

    val adapter = SellsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sells, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        vender.setOnClickListener {
            val directions = SellsListFragmentDirections.toQuickSell()
            Navigation.findNavController(it).navigate(directions)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    fun setupToolbar(){
        (requireActivity() as NavigationActivity).title = "Minhas vendas"
        (requireActivity() as NavigationActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as NavigationActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }

    fun updateView(){
        if(App.SellsList.data.isEmpty()){
            empty_view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }else{
            empty_view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            adapter.submitData(App.SellsList.data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onItemRemoved(id: String) {
        App.SellsList.data.remove(App.SellsList.data.find {
            it.id == id
        })
        adapter.submitData(App.SellsList.data)
        adapter.notifyDataSetChanged()

        updateView()
    }

}