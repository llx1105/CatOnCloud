package com.example.lxliang.catoncloud.presenter

import android.util.Log
import com.example.lxliang.catoncloud.model.GetNearbyCatResponse
import com.example.lxliang.catoncloud.network.HttpManagerFactory
import rx.Subscriber

/**
 * Created by lxliang on 04/12/2017.
 */
class NearbyCatPresenter(private val mCatsNearbyView: NearbyCatContract.View) : NearbyCatContract.Presenter {
    private val TAG = "NearbyCatPresenter"

    override fun start() {
        Log.i(TAG, "start")
    }

    override fun loadingCatsNearbyList() {
        HttpManagerFactory.getHttpManager().getCat(object : Subscriber<GetNearbyCatResponse>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {
                mCatsNearbyView.onGetDetailFail(e.toString())
            }

            override fun onNext(t: GetNearbyCatResponse?) {
                mCatsNearbyView.onGetDataSucceed(t!!.moments)
            }
        })
    }

    override fun stop() {
        Log.i(TAG, "stop")
    }

}