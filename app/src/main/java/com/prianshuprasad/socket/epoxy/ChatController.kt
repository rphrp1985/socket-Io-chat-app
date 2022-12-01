package com.prianshuprasad.socket.epoxy

import com.airbnb.epoxy.EpoxyController
import com.prianshuprasad.socket.model.Message
import com.prianshuprasad.socket.ui.fragment.chat.ChatFragment


class ChatController(listener: ChatFragment) : EpoxyController(){

    var items : ArrayList<Message>

    val listener= listener
    init {
        items= ArrayList()
    }

    fun update(x:ArrayList<Message>){
        items= x

    }

    override fun buildModels() {
        var i:Long =0

        items.forEach {msg ->

            ChatModel_().id(i++).msg_sender(msg.sender).username(msg.username).messageText(msg.text).addTo(this)


        }
    }

}


