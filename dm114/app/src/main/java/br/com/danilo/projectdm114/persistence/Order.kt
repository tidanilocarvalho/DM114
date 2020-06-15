package br.com.danilo.projectdm114.persistence

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Order (
    @Exclude var id: String? = null,
    var userId: String? = null,
    var status: String? = null,
    var productCode: String? = null,
    var orderId: String? = null,
    var date: Long? = null
)