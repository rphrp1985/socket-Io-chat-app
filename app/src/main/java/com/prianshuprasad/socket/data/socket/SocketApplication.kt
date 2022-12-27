package com.prianshuprasad.socket.data.socket

import io.socket.client.IO
import javax.inject.Inject

class  SocketApplication @Inject constructor(private val serverUrl: String) {

    private  var socket: io.socket.client.Socket? =  IO.socket(serverUrl)

    fun getSocket(): io.socket.client.Socket? = socket

}