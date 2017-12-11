package com.example.lxliang.catoncloud.presenter

/**
 * Created by lxliang on 06/12/2017.
 */
interface MainContract {
    interface View : BaseView<Presenter> {
        fun switchToNearbyCat()
        fun switchToMyCat()
    }

    interface Presenter : BasePresenter {
        fun switchToNearbyCat()
        fun switchToMyCat()
    }
}