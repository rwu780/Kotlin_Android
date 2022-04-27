package com.example.android.eggtimernotifications

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.eggtimernotifications.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(message: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${message.from}")

        // TODO Step 3.5 check messages for data
        // Check if message contains a data payload.
        message.data.let {
            Log.d(TAG, "onMessageReceived: " + message.data)
        }


        // TODO Step 3.6 check messages for notification and call sendNotification
        // Check if message contains a notification payload.
        message.notification?.let {
            Log.d(TAG, "onMessageReceived: ${it.body}")
            sendNotification(it.body as String)
        }

    }
    // [END receive_message]

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: $token")

        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Persist token to third-party (your app) servers.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}