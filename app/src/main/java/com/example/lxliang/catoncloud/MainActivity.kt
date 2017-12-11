package com.example.lxliang.catoncloud

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.lxliang.catoncloud.activity.HomeActivity
import com.example.lxliang.catoncloud.fragment.MyCatFragment
import com.example.lxliang.catoncloud.fragment.NearbyCatFragment
import com.example.lxliang.catoncloud.presenter.LoginContract
import com.example.lxliang.catoncloud.presenter.LoginPresenter

class MainActivity : AppCompatActivity(), LoginContract.View {


    private lateinit var mBtnLogin: Button
    private lateinit var mEditUsername: EditText
    private lateinit var mEditPassword: EditText
    private val TAG: String = "MainActivity"
    private val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        mBtnLogin = findViewById(R.id.button_login) as Button
        mEditUsername = findViewById(R.id.user_name) as EditText
        mEditPassword = findViewById(R.id.password) as EditText

        mBtnLogin.setOnClickListener() {
            Log.i("cat", String.format("Button click ,user name: %s, pwd: %s", mEditUsername.text, mEditPassword.text))
            presenter.startLogin(mEditUsername.text.toString(), mEditPassword.text.toString())
        }


    }

    override fun loginSuccess() {
        Log.d(TAG, "login succesful")
        startActivity(Intent(this, HomeActivity::class.java))
        finish() //will not return to this activity
    }





    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}
