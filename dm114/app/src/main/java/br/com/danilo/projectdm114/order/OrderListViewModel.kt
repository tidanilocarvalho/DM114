package br.com.danilo.projectdm114.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.persistence.Order
import br.com.danilo.projectdm114.persistence.OrderRepository

private const val TAG = "OrderListViewModel"

class OrderListViewModel : ViewModel() {

    private var _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    init {
        Log.d(TAG, "init ViewModel")
        getOrders()
    }

    private fun getOrders() {
        Log.d(TAG, "get orders")

        _orders = OrderRepository.getOrders()
    }

    override fun onCleared() {
        Log.i(TAG, "onCleared")
        super.onCleared()
    }

}