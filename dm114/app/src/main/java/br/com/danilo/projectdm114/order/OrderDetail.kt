package br.com.danilo.projectdm114.order

data class OrderDetail (
    var username: String,
    var orderId: String,
    var status: String,
    var productCode: String
)