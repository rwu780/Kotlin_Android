package com.example.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val intentAction = intent.action

        val toastMsg = when(intentAction){
            Intent.ACTION_POWER_CONNECTED -> "Power connected"
            Intent.ACTION_POWER_DISCONNECTED -> "Power disconnected"
            ACTION_CUSTOM_BROADCAST -> {
                val value = intent.getIntExtra(intent_key, 0)
                "Custom Broadcast Received: ${value}"
            }
            Intent.ACTION_HEADSET_PLUG -> "Unplug Headset"
            else -> "Else ..."
        }

        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()



    }
}