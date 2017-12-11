package com.example.lxliang.catoncloud.presenter

/**
 * Created by lxliang on 06/12/2017.
 */
class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun switchToNearbyCat() {
        view.switchToNearbyCat()
    }

    override fun switchToMyCat() {
        view.switchToMyCat()
    }
}