package com.prianshuprasad.socket.ui.fragment.chat

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.epoxy.ChatController
import com.prianshuprasad.socket.model.Message
import com.prianshuprasad.socket.ui.activities.MainActivity
import io.socket.client.On.Handle

class ChatFragment(listner:MainActivity,chatViewModel: ChatViewModel) : Fragment() {


    private lateinit var viewModel: ChatViewModel
    private lateinit var recyclerView:EpoxyRecyclerView
    private lateinit var send:ImageButton
    private lateinit var chatController:ChatController
    private  var arr:ArrayList<Message> = ArrayList()
    private lateinit var messagebox:EditText

   private val listner= listner
    private var chatViewModel= chatViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView= binding.findViewById<EpoxyRecyclerView>(R.id.recycler_chat)
        chatController = ChatController(this)
        send= binding.findViewById(R.id.button_send)
        messagebox= binding.findViewById(R.id.edit_message)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd= true

        recyclerView.layoutManager= linearLayoutManager
        recyclerView.adapter= chatController.adapter
        chatController.requestModelBuild()



        send.setOnClickListener {

            listner.attemptSend(messagebox.text.toString())
            chatViewModel.addMessages(Message("me",messagebox.text.toString(),0))

            messagebox.setText("")



        }



        chatViewModel.getMessages().observeForever{

            arr= it as ArrayList<Message>
            update()

            Handler().postDelayed({
                recyclerView.scrollToPosition(arr.size-1)

            },500)
         }

        return binding.rootView
    }

    fun update(){
        chatController.update(arr)
        chatController.requestModelBuild()
    }


    fun notifyUser(str:String){

        Toast.makeText(requireContext(),"$str",Toast.LENGTH_LONG).show()

    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}