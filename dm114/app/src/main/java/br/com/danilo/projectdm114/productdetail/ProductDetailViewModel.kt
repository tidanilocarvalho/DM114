package br.com.danilo.projectdm114.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.orderfcm.OrderDetail
import br.com.danilo.projectdm114.persistence.OrderRepository

class ProductDetailViewModel : ViewModel() {

    val productDetail = MutableLiveData<ProductDetail>()

    fun deleteOrder() {
        if (productDetail.value?.id != null) {
            OrderRepository.deleteOrder(productDetail.value!!.id)
            productDetail.value = null;
        }
    }
}