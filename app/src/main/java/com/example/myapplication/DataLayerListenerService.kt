package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import java.nio.charset.StandardCharsets
import java.util.concurrent.CancellationException

class DataLayerListenerService : WearableListenerService() {

    private val messageClient by lazy { Wearable.getMessageClient(this) }


    @SuppressLint("VisibleForTests")
    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)

        dataEvents.forEach { dataEvent ->
            val uri = dataEvent.dataItem.uri
            when (uri.path) {
                COUNT_PATH -> {
                        try {
                            val nodeId = uri.host!!
                            val payload = uri.toString().toByteArray()
                            messageClient.sendMessage(
                                nodeId,
                                DATA_ITEM_RECEIVED_PATH,
                                payload
                            )
                            Log.d("DataLayerListenerService", "Message sent successfully")
                        } catch (cancellationException: CancellationException) {
                            throw cancellationException
                        } catch (exception: Exception) {
                            Log.d("DataLayerListenerService", "Message failed")
                        }
                    }
                }
            }
        }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        Toast.makeText(getApplication(), "${messageEvent.data}", Toast.LENGTH_SHORT).show()
        when (messageEvent.path) {
            messagePath -> {
                val message = String(messageEvent.data, StandardCharsets.UTF_8)
                Log.d("MyWearableListener", "Message received: $message")

                // 워치 앱에서 수신한 메시지 처리 코드
            }
          START_ACTIVITY_PATH -> {
              Log.d(TAG, "onMessageReceived ${messageEvent.path}")
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val TAG = "DataLayerService"

        private const val messagePath = "/message"

        private const val START_ACTIVITY_PATH = "/astart-activity"
        private const val DATA_ITEM_RECEIVED_PATH = "/1234data-item-received"
        const val COUNT_PATH = "/count"
    }
}