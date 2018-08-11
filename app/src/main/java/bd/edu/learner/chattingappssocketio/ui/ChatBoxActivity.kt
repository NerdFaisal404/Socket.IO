package bd.edu.learner.chattingappssocketio.ui

import bd.edu.learner.chattingappssocketio.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import bd.edu.learner.chattingappssocketio.constants.Constants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import android.widget.Toast
import bd.edu.learner.chattingappssocketio.adapter.ChatBoxAdapter
import bd.edu.learner.chattingappssocketio.model.Message
import kotlinx.android.synthetic.main.activity_chat_box.*
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.json.JSONException
import org.json.JSONObject


class ChatBoxActivity : AppCompatActivity(), View.OnClickListener {

    private var socket: Socket? = null
    private var nickName: String? = null
    var messageList: kotlin.collections.MutableList<Message>? = null
    var chatBoxAdapter: ChatBoxAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)
        nickName = intent.extras.getString(Constants.NICK_NAME)
        messageList = java.util.ArrayList()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        rc_messageList.layoutManager = mLayoutManager
        rc_messageList.itemAnimator = DefaultItemAnimator()
        btn_send.setOnClickListener(this)
        initSocket()
        initUserJoin()
        disconnect()
        initMessageResponse()


    }

    private fun initSocket() {
        try {
            socket = IO.socket("http://10.174.23.251:3000")
            socket?.connect()
            socket?.emit("join", nickName)


        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun initUserJoin() {
        //implementing socket listeners
        socket?.on("userjoinedthechat", Emitter.Listener { args ->
            runOnUiThread {

                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initMessageResponse() {
        socket?.on("message", Emitter.Listener { args ->
            runOnUiThread {

                val data: JSONObject = args[0] as JSONObject
                try {
                    var nickName = data.getString("senderNickname")
                    var message = data.getString("message")

                    var messageObject = Message(nickName, message)
                    messageList?.add(messageObject)
                    chatBoxAdapter = ChatBoxAdapter(messageList!!);
                    chatBoxAdapter!!.notifyDataSetChanged();

                    rc_messageList.setAdapter(chatBoxAdapter);
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun disconnect() {
        socket!!.on("userdisconnect", Emitter.Listener { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(view: View?) {
        var messssage = edt_message.text.toString()
        socket?.emit("messagedetection", nickName, messssage)
        edt_message.setText(" ")
    }


    override fun onDestroy() {
        super.onDestroy()
        socket!!.disconnect();
    }

}
