package br.com.danilo.projectdm114.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.orderfcm.OrderDetail
import br.com.danilo.projectdm114.persistence.OrderRepository

class ProductDetailViewModel : ViewModel() {

    val productDetail = MutableLiveData<ProductDetail>()

    fun deleteOrder() {
        if (productDetail.value?.orderId != null) {
            OrderRepository.deleteOrder(productDetail.value!!.orderId)
            productDetail.value = null;
        }
    }
}