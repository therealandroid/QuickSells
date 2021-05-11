package com.diogojayme.quicksells.sells_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diogojayme.quicksells.R
import com.diogojayme.quicksells.model.Sell


class SellsAdapter() : RecyclerView.Adapter<ProductViewHolder>() {

    var productList: List<Sell> = mutableListOf()

    fun submitData(it: List<Sell>){
        this.productList = it
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {


        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adapter_sell,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    private fun getItem(position: Int): Sell{
        return productList[position]
    }
    override fun getItemCount(): Int {
        return productList.size
    }
}
