package com.prianshuprasad.socket.epoxy

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.prianshuprasad.socket.R
import com.prianshuprasad.socket.model.Message
import com.prianshuprasad.socket.ui.fragment.chat.ChatFragment


@EpoxyModelClass(layout = R.layout.first_message)
abstract class ChatModel :   EpoxyModelWithHolder<ChatModel.Holder>(){





    @EpoxyAttribute
    var username:String? = ""

    @EpoxyAttribute
    var messageText:String = ""

    @EpoxyAttribute
    var msg_sender:Int=0;


    override fun bind(holder: Holder) {

        holder.other_username.text= username;
        holder.my_messageView.text= messageText
        holder.oth_messageView.text = messageText

        if(msg_sender==0) {
            holder.other_view.visibility = View.GONE
            holder.my_view.visibility= View.VISIBLE
        }else{
            holder.other_view.visibility = View.VISIBLE
            holder.my_view.visibility= View.GONE

        }

    }

    inner class Holder : EpoxyHolder(){

        lateinit var my_messageView: TextView
        lateinit var oth_messageView:TextView
        lateinit var my_view:ConstraintLayout
        lateinit var other_username:TextView
        lateinit var other_view:ConstraintLayout



        override fun bindView(itemView: View) {

            my_messageView= itemView?.findViewById(R.id.text_chat_message_me)!!
            oth_messageView = itemView?.findViewById(R.id.text_chat_message_other)!!
            my_view = itemView?.findViewById(R.id.my_view)!!
            other_username= itemView?.findViewById(R.id.other_username)!!
            other_view = itemView?.findViewById(R.id.other_view)!!




        }

    }

    override fun getDefaultLayout(): Int {
       return R.layout.first_message
    }


}

