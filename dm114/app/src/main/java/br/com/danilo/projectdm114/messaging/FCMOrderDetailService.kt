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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

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

            if (remoteMessage.data.containsKey(ORDER_DETAIL_MESSAGING_KEY)) {
                sendOrderDetailNotification(remoteMessage.data.get(ORDER_DETAIL_MESSAGING_KEY)!!)
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