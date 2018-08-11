package bd.edu.learner.chattingappssocketio.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bd.edu.learner.chattingappssocketio.R
import bd.edu.learner.chattingappssocketio.model.Message
import kotlinx.android.synthetic.main.item_message.view.*


class ChatBoxAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickName = view.nickName
        val message = view.message
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBoxAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val m = messageList.get(position)
        holder.nickName.setText(m.nickName + " : ")
        holder.message.setText(m.message)


    }


}
