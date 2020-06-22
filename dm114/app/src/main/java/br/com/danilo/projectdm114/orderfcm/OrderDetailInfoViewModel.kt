package br.com.danilo.projectdm114.orderfcm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.danilo.projectdm114.orderfcm.OrderDetail

class OrderDetailInfoViewModel : ViewModel() {

    val fcmRegistrationId = MutableLiveData<String>()
    val orderDetail = MutableLiveData<OrderDetail>()

}