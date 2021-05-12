package com.diogojayme.quicksells.sells_list


import android.text.format.DateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.diogojayme.quicksells.model.Sell
import kotlinx.android.synthetic.main.item_adapter_sell.view.*
import java.util.*

public interface OnItemDeletedListener {
    fun onItemRemoved(id: String)
}
class ProductViewHolder(itemView: View, var listener: OnItemDeletedListener) : RecyclerView.ViewHolder(itemView) {


    public fun bind(sell: Sell) {
        itemView.setOnClickListener {  }
        itemView.total.text = "R$ ${String.format("%.2f", sell.price)}"
        itemView.profit.text = "R$ ${String.format("%.2f", sell.profit)}"
        itemView.costPrice.text = "R$ ${String.format("%.2f", sell.costPrice)}"
        itemView.data.text = "${dayOfWeek(sell.date)}-${month(sell.date)}\n${year(sell.date)}"

        itemView.delete.setOnClickListener{
            listener.onItemRemoved(sell.id)
        }
    }


    fun dayOfWeek(date: Date): String {
        return DateFormat.format("dd", date) as String // 20
    }


    fun month(date: Date): String {
        return DateFormat.format("MMM", date) as String // Jun
    }

    fun year(date: Date): String {
        return DateFormat.format("yyyy", date) as String // 2013
    }


}
