package com.example.lxliang.catoncloud.network

/**
 * Created by lxliang on 04/12/2017.
 */
class HttpManagerFactory {
    companion object {
        private val sInstance: HttpManager = HttpManagerImpl()

        fun getHttpManager(): HttpManager {
            return sInstance
        }
    }
}