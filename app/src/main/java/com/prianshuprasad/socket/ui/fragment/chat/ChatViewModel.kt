package com.prianshuprasad.socket.ui.fragment.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prianshuprasad.socket.model.Message

class ChatViewModel : ViewModel() {

    private val arr_messages = MutableLiveData<ArrayList<Message>>()

    fun getMessages(): LiveData<ArrayList<Message>> {
        return arr_messages
    }
    fun addMessages(msg:Message){

        var arr_temp = arr_messages.value
        if(arr_temp==null){
            arr_temp= ArrayList()
        }
        arr_temp.add(msg)
        arr_messages.value= arr_temp!!

//        getMessages()

    }



}