package com.prianshuprasad.socket.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.model.Message
import com.prianshuprasad.socket.model.SocketApp
import com.prianshuprasad.socket.ui.fragment.chat.ChatFragment
import com.prianshuprasad.socket.ui.fragment.chat.ChatViewModel
import com.prianshuprasad.socket.ui.fragment.login.LoginFragment
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.lang.Thread.sleep
import kotlin.concurrent.thread


 var socket: Socket?= null
private lateinit var frameLayout: FrameLayout
private lateinit var chatFragment: ChatFragment
private lateinit var loginFragment: LoginFragment
private lateinit var chatViewModel: ChatViewModel
var url = "https://socketio-chat-h9jt.herokuapp.com/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatViewModel= ViewModelProvider(this).get(ChatViewModel::class.java)


        supportActionBar?. hide()
        frameLayout= findViewById(R.id.frame_layout)
        chatFragment = ChatFragment(this, chatViewModel)
        loginFragment= LoginFragment(this)
        socket= SocketApp(url).getSocket()

        if(socket==null){
            Toast.makeText(this,"Socket Error",Toast.LENGTH_LONG).show()
            finish()
        }


        setUpSocket()

        openLogin()





    }

    fun openLogin(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout,
            loginFragment).commit()
    }

    fun openChat(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout,
            chatFragment).commit()
    }



    fun setUpSocket(){

        thread {
            socket?.on(Socket.EVENT_CONNECT, onConnect)
//            socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
            socket?.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            socket?.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            socket?.on("new message", onNewMessage)
//            socket.on("user joined", onUserJoined)
//            socket.on("user left", onUserLeft)
//            socket.on("typing", onTyping)
//            socket.on("stop typing", onStopTyping)
            socket?.on("login", onLogin)
            socket?.connect()
        }
    }



     fun attemptLogin(name:String) {
         thread {
             socket?.emit("add user", "$name");

         }

    }

    private val onLogin = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val numUsers: Int
        numUsers = try {
            data.getInt("numUsers")
        } catch (e: JSONException) {
            return@Listener
        }
        runOnUiThread {
        Toast.makeText(this,"Login SucessFul",Toast.LENGTH_LONG).show()
            openChat()
        }

    }

    private val onConnect = Emitter.Listener { args ->

        runOnUiThread { Toast.makeText(this,"connected ",Toast.LENGTH_LONG).show() }

    }




     fun attemptSend(str:String) {

         thread {
             socket?.emit("new message", str)
         }
    }


    private val onConnectError = Emitter.Listener {
        this.runOnUiThread(Runnable {

            Toast.makeText(this,
                "connection eror", Toast.LENGTH_LONG).show()
        })
    }




    val onNewMessage = Emitter.Listener { args ->
        this.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username")
                message = data.getString("message")
                chatViewModel.addMessages(Message(username,message,1))

            } catch (e: JSONException) {
                return@Runnable
            }

        })
    }













}