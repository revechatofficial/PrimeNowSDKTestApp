package com.app.revechatsdktestapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp
import com.revesoft.revechatsdk.primenow.ChatBotEventListener
import com.revesoft.revechatsdk.primenow.REVEChatPrimeNowSdk
import com.revesoft.revechatsdk.primenow.SessionKeepAliveListener
import com.revesoft.revechatsdk.utils.REVEChatAppLifecycleObserver
import timber.log.Timber

class MyApplication : Application(), SessionKeepAliveListener, ChatBotEventListener {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(REVEChatAppLifecycleObserver.INSTANCE)

        REVEChatPrimeNowSdk.setSessionKeepAliveListener(this)
        REVEChatPrimeNowSdk.setChatBotEventListener(this)
    }

    override fun onUserActive() {
        Timber.i("onUserActive()")
    }

    override fun onChatBotEvent(eventType: String) {
        Timber.i("onChatBotEvent() eventType = %s", eventType)

        // Handle the event based on the eventType
        Toast.makeText(this, "onChatBotEvent => $eventType", Toast.LENGTH_LONG).show()
    }
}