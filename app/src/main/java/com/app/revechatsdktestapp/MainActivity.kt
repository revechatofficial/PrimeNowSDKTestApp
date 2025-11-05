package com.app.revechatsdktestapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.app.revechatsdktestapp.webhook.REVEChatWebHook
import com.google.gson.Gson
import com.revesoft.revechatsdk.data.remote.getglobal.RetrofitClient.userService
import com.revesoft.revechatsdk.data.remote.getglobal.TokenVerifyResponse
import com.revesoft.revechatsdk.event.REVEChatEventListener
import com.revesoft.revechatsdk.event.REVEChatEventManager.registerListener
import com.revesoft.revechatsdk.model.VisitorInfo
import com.revesoft.revechatsdk.service.REVEChatApiService
import com.revesoft.revechatsdk.ui.activity.ReveChatActivity
import com.revesoft.revechatsdk.utils.ReveChat
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {  //, REVEChatEventListener

    private lateinit var accountIdEditText: EditText
    private lateinit var userNameEditText: EditText
    private lateinit var emailIdEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var loginStateCheckBox: CheckBox


    private val accountId = "4737559"
    private val userName = "androidSDKLib"
    private val userEmail = "androidSDK@test.com"
    private val userPhone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 35) {
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        accountIdEditText = findViewById(R.id.accountID)
        accountIdEditText.setText(accountId)
        accountIdEditText.isEnabled = false
        userNameEditText = findViewById(R.id.userName)
        userNameEditText.setText(userName)
        emailIdEditText = findViewById(R.id.userEmail)
        emailIdEditText.setText(userEmail)
        phoneNumberEditText = findViewById(R.id.userPhone)
        phoneNumberEditText.setText(userPhone)

        loginStateCheckBox = findViewById(R.id.login_state_checkbox)


        findViewById<Button>(R.id.startChat).setOnClickListener {
            openChatWindow()
        }


        initiateReveChat()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!REVEChatApiService.isCallRunning())
            stopService(Intent(this, REVEChatApiService::class.java))
    }


    private fun openChatWindow() {
        startActivity(Intent(this, ReveChatActivity::class.java))
    }



    private fun initiateReveChat() {
        ReveChat.init(accountId)

        //create visitor info
        val visitorInfo: VisitorInfo = VisitorInfo.Builder()
            .name(userName) // set name
            .email(userEmail)
            .phoneNumber(userPhone)
            .build()

        ReveChat.setVisitorInfo(visitorInfo)

        ReveChat.setAppBundleName(BuildConfig.APPLICATION_ID)
        ReveChat.setAppVersionNumber(BuildConfig.VERSION_NAME)

        ReveChat.setApiServiceTitle("MyAppName")  // change as per your requirement
        ReveChat.setApiServiceContent("Support") // change as per your requirement

        ReveChat.setIsScreenShotEnabled(false)


        startService(Intent(this, REVEChatApiService::class.java))
    }


//    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            Toast.makeText(this, "RESULT_OK", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Operation Canceled", Toast.LENGTH_SHORT).show()
//        }
//    }





}