package br.com.danilo.projectdm114.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.orderfcm.OrderDetail

class ProductDetailViewModel : ViewModel() {

    val productDetail = MutableLiveData<ProductDetail>()

}