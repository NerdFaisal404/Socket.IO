package bd.edu.learner.chattingappssocketio.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import bd.edu.learner.chattingappssocketio.R
import bd.edu.learner.chattingappssocketio.constants.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : View.OnClickListener, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_enterChat.setOnClickListener(this)



    }

    override fun onClick(view: View?) {
        val nickName = edt_nickName.text.toString()
        if (!TextUtils.isEmpty(nickName)) {
            val intent = Intent(this@MainActivity, ChatBoxActivity::class.java)
            intent.putExtra(Constants.NICK_NAME, nickName)
            startActivity(intent)
        }
    }
}
