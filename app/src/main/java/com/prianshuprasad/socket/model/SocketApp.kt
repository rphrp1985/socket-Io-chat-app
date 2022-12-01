package com.prianshuprasad.socket.model

import io.socket.client.IO
import io.socket.client.Socket

class SocketApp(){

var socket: Socket?=null

constructor(url:String) : this() {
try {


    socket = IO.socket(url);
}catch (e:Exception){
    socket=null
}

}

@JvmName("getSocket1")
fun getSocket()= socket





}