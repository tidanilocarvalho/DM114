package br.com.danilo.projectdm114.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.orderfcm.OrderDetail
import br.com.danilo.projectdm114.persistence.Order
import br.com.danilo.projectdm114.persistence.OrderRepository

class ProductDetailViewModel : ViewModel() {

    var order = MutableLiveData<Order>()

    fun getOrder() {
        order
    }

    fun deleteOrder() {
        if (order.value?.id != null) {
            OrderRepository.deleteOrder(order.value?.id!!)
            order.value = null;
        }
    }
}