package br.com.danilo.projectdm114.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderDetailInfoViewModel : ViewModel() {

    val fcmRegistrationId = MutableLiveData<String>()
    val orderDetail = MutableLiveData<OrderDetail>()

}