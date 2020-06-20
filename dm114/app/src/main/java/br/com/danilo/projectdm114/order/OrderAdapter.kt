package br.com.danilo.projectdm114.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.danilo.projectdm114.databinding.ItemOrderBinding
import br.com.danilo.projectdm114.persistence.Order

class OrderAdapter(val onOrderClickListener: OrderClickListener) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)

        holder.itemView.setOnClickListener {
            onOrderClickListener.onClick(order)
        }

        holder.itemView.setOnLongClickListener {
            true
        }
    }

    class OrderViewHolder(private var binding: ItemOrderBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order
            binding.executePendingBindings()
        }
    }

    companion object OrderDiff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return false
        }
        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return false
        }
    }

    class OrderClickListener(val clickListener: (order: Order) -> Unit) {
        fun onClick(order: Order) = clickListener(order)
    }
}