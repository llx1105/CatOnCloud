package com.example.lxliang.catoncloud.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.lxliang.catoncloud.MainActivity
import com.example.lxliang.catoncloud.R
import com.example.lxliang.catoncloud.presenter.LoginContract
import com.example.lxliang.catoncloud.presenter.LoginPresenter
import com.example.lxliang.catoncloud.utils.AnimationUtil
import com.example.lxliang.catoncloud.utils.KeyboardWatcher

/**
 * Created by lxliang on 04/12/2017.
 */
class LoginActivity : AppCompatActivity(), KeyboardWatcher.SoftKeyboardStateListener, LoginContract.View {
    private val TAG = "LoginActivity"
    private val presenter = LoginPresenter(this)

    private var screenHeight = 0

    @BindView(R.id.btn_login)
    lateinit var mBtnLogin: View

    @BindView(R.id.logo_layout)
    lateinit var mLogoLayout: View

    @BindView(R.id.input_username)
    lateinit var mUsernameEt: EditText

    @BindView(R.id.input_password)
    lateinit var mPasswordEt: EditText

    @BindView(R.id.content)
    lateinit var mContent: View

    @OnClick(R.id.btn_login)
    fun startLogin() {
        presenter.startLogin(mUsernameEt.text.toString(), mPasswordEt.text.toString())
    }

    private lateinit var keyboardWatcher: KeyboardWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        screenHeight = resources.displayMetrics.heightPixels
        keyboardWatcher = KeyboardWatcher((findViewById(Window.ID_ANDROID_CONTENT)))
        keyboardWatcher.addSoftKeyboardStateListener(this)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "unResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        keyboardWatcher.removeSoftKeyboardStateListener(this)
    }

    override fun onSoftKeyboardOpened(keyboardHeightInPx: Int) {
        val location = IntArray(2)
        mContent.getLocationOnScreen(location)
        val blankSpace = screenHeight - (location[1] + mContent.height)

        if (keyboardHeightInPx > blankSpace) {
            val dest = (keyboardHeightInPx - blankSpace).toFloat()

            AnimationUtil.moveContentUp(mContent, dest)
            AnimationUtil.zoomIn(mLogoLayout, dest)
        }
    }

    override fun onSoftKeyboardClosed() {
        AnimationUtil.moveContentDown(mContent)
        AnimationUtil.zoomOut(mLogoLayout)
    }

    override fun loginSuccess() {
        Log.d(TAG, "login successful")
        startActivity(Intent(this, MainActivity::class.java))

        //clear current activity from stack. As we don't need to go back to login page
        finish()
    }
}