# DM114
DM114 HOMEWORK

# FMC

Authorization : Key=<TOKEN>

POST : https://fcm.googleapis.com/fcm/send

{
	"to" : "<TOKEN>",
	"data" : {
		"orderDetail": "{\"username\": \"danilo@gmail.com\", \"orderId\": 2, \"status\": \"Pedido entregue\", \"productCode\": \"COD2\"}"
	}
}
