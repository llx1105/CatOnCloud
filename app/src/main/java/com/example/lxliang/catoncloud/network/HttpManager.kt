package com.example.lxliang.catoncloud.network

import com.example.lxliang.catoncloud.model.GetNearbyCatResponse
import rx.Subscriber

/**
 * Created by lxliang on 04/12/2017.
 */
interface HttpManager {
    fun getCat(callback: Subscriber<GetNearbyCatResponse>)
}