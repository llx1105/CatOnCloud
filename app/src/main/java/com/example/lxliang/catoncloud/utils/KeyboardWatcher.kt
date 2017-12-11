package com.example.lxliang.catoncloud.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import java.util.*

/**
 * Created by lxliang on 06/12/2017.
 */
class KeyboardWatcher @JvmOverloads constructor(private val activityRootView: View, private var isSoftKeyboardOpend: Boolean = false) : ViewTreeObserver.OnGlobalLayoutListener {

    interface SoftKeyboardStateListener {
        fun onSoftKeyboardOpened(keyboardHeightInPx: Int)

        fun onSoftKeyboardClosed()
    }

    private val listeners = LinkedList<SoftKeyboardStateListener>()
    private var lastSoftKeyboardHeightInPx: Int = 0
    private var statusBarHeight: Int = -1

    init {
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener(this)
        val resourceId = activityRootView.context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = activityRootView.context.resources.getDimensionPixelSize(resourceId)
        }
    }

    override fun onGlobalLayout() {
        val r = Rect()
        activityRootView.getWindowVisibleDisplayFrame(r)

        val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
        if (!isSoftKeyboardOpend && heightDiff > activityRootView.rootView.height / 4) {
            isSoftKeyboardOpend = true
            if (activityRootView.context is Activity && !isFullScreen(activityRootView.context as Activity)) {
                notifyOnSoftKeyboardOpened(heightDiff - statusBarHeight)
            } else {
                notifyOnSoftKeyboardOpened(heightDiff)
            }
        } else if (isSoftKeyboardOpend && heightDiff < activityRootView.rootView.height / 4) {
            isSoftKeyboardOpend = false
            notifyOnSoftKeyboardClosed()
        }
    }

    fun addSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx

        for (listener in listeners) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener.onSoftKeyboardClosed()
        }
    }

    private fun isFullScreen(activity: Activity): Boolean {
        return activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
    }
}