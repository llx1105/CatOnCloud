package com.example.lxliang.catoncloud.network

import com.example.lxliang.catoncloud.model.GetNearbyCatResponse
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by lxliang on 04/12/2017.
 */
class HttpManagerImpl : HttpManager {
    override fun getCat(callback: Subscriber<GetNearbyCatResponse>) {
        CatService.getCatService().getNearbyCat()
                .subscribeOn(Schedulers.io())
                .observeOn((AndroidSchedulers.mainThread()))
                .subscribe(callback)
    }
}