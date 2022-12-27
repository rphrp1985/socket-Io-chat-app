package com.prianshuprasad.socket.ui.fragment.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.ui.ViewModel.SocketViewModel
import com.prianshuprasad.socket.ui.activities.MainActivity

class LoginFragment(listner:MainActivity, socketViewModel: SocketViewModel) : Fragment() {

    private lateinit var username:TextView
    private lateinit var submitButton:Button
    val listner= listner
    private val socketViewModel = socketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_login, container, false)

        username= binding.findViewById(R.id.username_input)
        submitButton= binding.findViewById(R.id.sign_in_button)

        submitButton.setOnClickListener {

             socketViewModel.attemptLogin(username.text.toString())

        }


        return binding.rootView
    }


}