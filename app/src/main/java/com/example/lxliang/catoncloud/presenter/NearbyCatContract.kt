package com.example.lxliang.catoncloud.presenter

import com.example.lxliang.catoncloud.model.GetNearbyCatResponse

/**
 * Created by lxliang on 04/12/2017.
 */
interface NearbyCatContract {
    interface View {
        //        fun showNearbyCats(catsNearby: ArrayList<CatsNearby>)
        fun onGetDataSucceed(data: List<GetNearbyCatResponse.MomentsBean>?)

        fun onGetDetailFail(msg: String)
    }

    interface Presenter {
        fun start()
        fun loadingCatsNearbyList()
        fun stop()
    }

}