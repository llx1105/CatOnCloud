package com.example.lxliang.catoncloud.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by lxliang on 04/12/2017.
 */
abstract class BaseFragment : Fragment()  {
    private var mRootView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = createView(inflater, container, savedInstanceState)
        }

        val parent = mRootView?.parent as ViewGroup?
        parent?.removeView(mRootView)
        return mRootView
    }

    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

}