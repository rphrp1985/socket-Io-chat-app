package com.prianshuprasad.socket.ui.ViewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prianshuprasad.socket.data.model.Message
//import com.prianshuprasad.socket.repositary.SocketRepo
import com.prianshuprasad.socket.data.socket.SocketApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class SocketViewModel @Inject constructor(private val socketApplication: SocketApplication):ViewModel() {


  private  val socket = socketApplication.getSocket()
    private val arr_messages = MutableLiveData<ArrayList<Message>>()
    private val isLoggedIn = MutableLiveData<Boolean>(false)
    private val userMessage = MutableLiveData<String>()


    fun getMessages(): LiveData<ArrayList<Message>> {
        return arr_messages
    }


    fun setUpSocket(){

        thread {
            socket?.on("connect", onConnect)
            socket?.on("connect_error", onConnectError)
            socket?.on("connect_timeout", onConnectError)
            socket?.on("new message", onNewMessage)
            socket?.on("login", onLogin)
            socket?.connect()
        }
    }


    fun getUserMessage()= userMessage


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
        Handler(Looper.getMainLooper()).post {
            isLoggedIn.value= true
        }


    }

    fun getLoginState()= isLoggedIn

    private val onConnect = Emitter.Listener { args ->

        Handler(Looper.getMainLooper()).post {
                    userMessage.value = "Connected"
        }


    }


     fun attemptSend(str:String) {
         thread {
             socket?.emit("new message", str)

         }
         addMessages(Message("",str,0))
    }


    private val onConnectError = Emitter.Listener {
        Handler(Looper.getMainLooper()).post {
            userMessage.value = "Connection error"
        }

    }



    fun addMessages(msg: Message){

        var arr_temp = arr_messages.value
        if(arr_temp==null){
            arr_temp= ArrayList()
        }
        arr_temp.add(msg)
        arr_messages.value= arr_temp!!

    }


    val onNewMessage = Emitter.Listener { args ->

            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username")
                message = data.getString("message")

                Handler(Looper.getMainLooper()).post {
                    addMessages(Message(username,message,1))
                }


            } catch (e: JSONException) {



            }


    }


    fun destroy(){
        socket?.close()
    }


}