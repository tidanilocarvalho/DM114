package br.com.danilo.projectdm114.orderlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import br.com.danilo.projectdm114.R
import br.com.danilo.projectdm114.databinding.FragmentOrdersListBinding
import br.com.danilo.projectdm114.productdetail.ProductDetailFragmentDirections

private const val TAG = "OrderListFragment"

class OrderListFragment : Fragment() {

    private val orderListViewModel: OrderListViewModel by lazy {
        ViewModelProviders.of(this).get(OrderListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        val binding = FragmentOrdersListBinding.inflate(inflater)

        Log.i(TAG, "onCreateView")

        binding.setLifecycleOwner(this)

        binding.orderListViewModel = orderListViewModel

        val itemDecor = DividerItemDecoration(getContext(), VERTICAL)
        binding.rcvOrders.addItemDecoration(itemDecor)

        binding.rcvOrders.adapter =
            OrderAdapter(OrderAdapter.OrderClickListener {
                Log.i(TAG, "Order selected: ${it.orderId}")
                this.findNavController()
                    .navigate(
                        ProductDetailFragmentDirections.actionShowProductDetail(it.id, it.productCode)
                    )
            })

        binding.ordersRefresh.setOnRefreshListener {
            Log.i(TAG, "Refreshing orders list")
            binding.ordersRefresh.isRefreshing = false
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.signout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}