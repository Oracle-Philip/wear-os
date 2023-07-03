package com.example.myapplication

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.wearable.*

class MainActivity : AppCompatActivity(), MessageClient.OnMessageReceivedListener, CapabilityClient.OnCapabilityChangedListener {

    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
    private val CAPABILITY_NAME = "mobile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener(this)
        capabilityClient.addLocalCapability(CAPABILITY_NAME)
    }

    override fun onPause() {
        //messageClient.removeListener(this)
        //capabilityClient.removeLocalCapability(CAPABILITY_NAME)
        super.onPause()
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        when (messageEvent.path) {
            "/start_activity" -> {
                val message = String(messageEvent.data)
                runOnUiThread {
                    Toast.makeText(this, "Message from smartphone: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
        Log.e("as", p0.toString())
    }
}
//class MainActivity : AppCompatActivity(), MessageClient.OnMessageReceivedListener {
//
//    private val messageClient by lazy { Wearable.getMessageClient(this) }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        messageClient.addListener(this)
//    }
//
//    override fun onPause() {
//        messageClient.removeListener(this)
//        super.onPause()
//    }
//
//    override fun onMessageReceived(messageEvent: MessageEvent) {
//        when (messageEvent.path) {
//            "/start_activity" -> {
//                val message = String(messageEvent.data)
//                runOnUiThread {
//                    Toast.makeText(this, "Message from smartphone: $message", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
//@SuppressLint("RestrictedApi")
//class MainActivity : AppCompatActivity() {
//    private val dataClient by lazy { Wearable.getDataClient(this) }
//    private val messageClient by lazy { Wearable.getMessageClient(this) }
//    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }
//    private val clientDataViewModel by viewModels<ClientDataViewModel>()
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//         setContentView(binding.root)
//
//    }
//
//    private fun displayNodes(nodes: Set<Node>) {
////        val message = if (nodes.isEmpty()) {
////            getString(R.string.no_device)
////        } else {
////            getString(R.string.connected_nodes, nodes.joinToString(", ") { it.displayName })
////        }
//
//        //Toast.makeText(this, "abcd", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        dataClient.addListener(clientDataViewModel)
//        messageClient.addListener(clientDataViewModel)
//        capabilityClient.addListener(
//            clientDataViewModel,
//            Uri.parse("wear://"),
//            CapabilityClient.FILTER_REACHABLE
//        )
//    }
//
//    override fun onPause() {
//        super.onPause()
//        dataClient.removeListener(clientDataViewModel)
//        messageClient.removeListener(clientDataViewModel)
//        capabilityClient.removeListener(clientDataViewModel)
//    }
//
//    companion object {
//        private const val TAG = "MainActivity"
//
//        private const val CAMERA_CAPABILITY = "camera"
//        private const val WEAR_CAPABILITY = "wear"
//        private const val MOBILE_CAPABILITY = "mobile"
//    }
//}
