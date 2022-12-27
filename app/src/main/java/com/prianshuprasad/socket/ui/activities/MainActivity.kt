package com.prianshuprasad.socket.ui.activities

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.ui.ViewModel.SocketViewModel
//import com.prianshuprasad.socket.ViewModel.ViewModel
import com.prianshuprasad.socket.ui.fragment.chat.ChatFragment
import com.prianshuprasad.socket.ui.fragment.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    var socket: Socket?= null
    private lateinit var frameLayout: FrameLayout
    private lateinit var chatFragment: ChatFragment
    private lateinit var loginFragment: LoginFragment
    private lateinit var socketViewModel: SocketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        socketViewModel =ViewModelProvider(this).get(SocketViewModel::class.java)

        supportActionBar?. hide()
        frameLayout= findViewById(R.id.frame_layout)
        chatFragment = ChatFragment(this, socketViewModel)
        loginFragment= LoginFragment(this,socketViewModel)


        socketViewModel.getLoginState().observeForever {
            if(it){
                openChat()
            }else
                openLogin()
        }

        socketViewModel.getUserMessage().observeForever {
            Toast.makeText(this,"$it",Toast.LENGTH_LONG).show()
        }
        socketViewModel.setUpSocket()

        openLogin()
    }

    fun openLogin(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout,
            loginFragment).commit()
    }

    fun openChat(){
        Toast.makeText(this,"Login SucessFul",Toast.LENGTH_LONG).show()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout,
            chatFragment).commit()
    }


    override fun onDestroy() {
        socketViewModel.destroy()
        super.onDestroy()
    }





}