package com.prianshuprasad.socket.utils.daggerhilt.module

import com.prianshuprasad.socket.data.socket.SocketApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import java.net.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 class AppModule {

    @Provides
    @Singleton
    fun getServerUrl():String="https://socketio-chat-h9jt.herokuapp.com/"


}