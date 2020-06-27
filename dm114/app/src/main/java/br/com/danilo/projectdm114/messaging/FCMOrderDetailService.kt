package br.com.danilo.projectdm114.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.danilo.projectdm114.R
import br.com.danilo.projectdm114.MainActivity
import br.com.danilo.projectdm114.orderfcm.OrderDetail
import br.com.danilo.projectdm114.persistence.Order
import br.com.danilo.projectdm114.persistence.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

private const val TAG = "FCMOrderDetailService"

const val NOTIFICATION_CHANNEL_ID = "1"
const val ORDER_DETAIL_MESSAGING_KEY = "orderDetail"
const val NOTIFICATION_CHANNEL_NAME = "Sales Provider"

class FCMOrderDetailService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Payload: " + remoteMessage.data)

            val loggedEmail = FirebaseAuth.getInstance().currentUser?.email

            if (remoteMessage.data.containsKey(ORDER_DETAIL_MESSAGING_KEY)) {
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<OrderDetail> = moshi.adapter<OrderDetail>(
                    OrderDetail::class.java)
                var orderDetail = jsonAdapter.fromJson(remoteMessage.data.get(ORDER_DETAIL_MESSAGING_KEY)!!)

                if (orderDetail?.username.equals(loggedEmail)!!) {
                    Log.d(TAG, "Message accepted")

                    val order = Order(
                        status = orderDetail?.status,
                        orderId = orderDetail?.orderId,
                        productCode = orderDetail?.productCode,
                        date = System.currentTimeMillis())
                    OrderRepository.saveOrder(order)
                    sendOrderDetailNotification(remoteMessage.data.get(ORDER_DETAIL_MESSAGING_KEY)!!)
                } else {
                    Log.d(TAG, "Message not accepted")
                }
            }
        }
    }

    private fun sendOrderDetailNotification(orderDetailInfo: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ORDER_DETAIL_MESSAGING_KEY, orderDetailInfo)
        sendNotification(intent)
    }

    private fun sendNotification(intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cloud_queue_black_24dp)
            .setContentTitle(NOTIFICATION_CHANNEL_NAME)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}