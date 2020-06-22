package br.com.danilo.projectdm114.order

import android.text.format.DateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.danilo.projectdm114.persistence.Order
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("ordersList")
fun bindProductsList(recyclerView: RecyclerView, orders: List<Order>?) {
    orders?.let {
        val adapter = recyclerView.adapter as OrderAdapter
        adapter.submitList(orders)
    }
}

@BindingAdapter("orderDate")
fun bindProductPrice(txtOrderDate: TextView, orderDate: Long?) {
    orderDate?.let {
        txtOrderDate.text = DateFormat.format("yyyy-MM-dd hh:mm:ss", Date(orderDate));
    }
}