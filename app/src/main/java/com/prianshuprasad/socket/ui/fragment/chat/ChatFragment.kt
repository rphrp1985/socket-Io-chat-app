package com.prianshuprasad.socket.ui.fragment.chat

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.ui.ViewModel.SocketViewModel
import com.prianshuprasad.socket.utils.epoxy.ChatController
import com.prianshuprasad.socket.data.model.Message
import com.prianshuprasad.socket.ui.activities.MainActivity

class ChatFragment(listner:MainActivity,socketViewModel: SocketViewModel) : Fragment() {


    private lateinit var recyclerView:EpoxyRecyclerView
    private lateinit var send:ImageButton
    private lateinit var chatController: ChatController
    private  var arr:ArrayList<Message> = ArrayList()
    private lateinit var messagebox:EditText
    private val listner= listner
    private val socketViewModel= socketViewModel

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

         socketViewModel.attemptSend(messagebox.text.toString())
            messagebox.setText("")



        }




        socketViewModel.getMessages().observeForever{

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




//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}