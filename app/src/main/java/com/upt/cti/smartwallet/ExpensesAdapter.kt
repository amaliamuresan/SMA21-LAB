package com.upt.cti.smartwallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.smartwallet.model.ExpenseItem

class ExpensesAdapter : RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    var items = listOf<ExpenseItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ExpensesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name_textView)
        var price: TextView = itemView.findViewById(R.id.price_textView)
        var date: TextView = itemView.findViewById(R.id.date_textView)
        var time: TextView = itemView.findViewById(R.id.time_textView)
        var category: TextView = itemView.findViewById(R.id.category_textView)
        var index: TextView = itemView.findViewById(R.id.index_textView)

        fun bindItems(item: ExpenseItem , position: Int) {
            name.text = item.name
            price.text = "${item.price} LEI"
            date.text = item.date
            time.text = item.time
            category.text = item.category
            index.text = position.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.expense_item, parent, false)
        return ExpensesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.bindItems(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}