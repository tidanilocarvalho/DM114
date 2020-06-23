package br.com.danilo.projectdm114.orderfcm

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.danilo.projectdm114.R
import br.com.danilo.projectdm114.databinding.FragmentOrderDetailInfoBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

private const val TAG = "OrderDetailInfoFragment"

class OrderDetailInfoFragment : Fragment() {
    private val orderDetailInfoViewModel: OrderDetailInfoViewModel by lazy {
        ViewModelProviders.of(this).get(OrderDetailInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderDetailInfoBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.orderDetailInfoViewModel = orderDetailInfoViewModel

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    orderDetailInfoViewModel.fcmRegistrationId.value = task.result?.token
                    Log.i(TAG, "FCM Token: ${task.result?.token}")
                }
            }

        if (this.arguments != null) {
            if (this.arguments!!.containsKey("orderDetailInfo")) {
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<OrderDetail> =
                    moshi.adapter<OrderDetail>(
                        OrderDetail::class.java)
                jsonAdapter.fromJson(this.arguments!!.getString("orderDetailInfo")!!).let {
                    orderDetailInfoViewModel.orderDetail.value = it
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.signout_menu, menu)
        inflater.inflate(R.menu.order_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}