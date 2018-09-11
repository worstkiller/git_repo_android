package com.incred.gitrepo

import android.support.v4.app.Fragment

/**
 * base fragment class for ui components with basic functionality
 * inheriting from base activity
 */
open class BaseFragment : Fragment() {

    /**
     * change the view from base fragment
     * @see BaseFragment
     * @see Boolean
     */
    fun changeView(fragment: BaseFragment, isBackStack: Boolean) {
        (activity as BaseActivity).changeView(fragment, isBackStack)
    }

    /**
     * show the message with base activity context
     * @see String
     */
    fun showToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    /**
     * set the message and show the progress
     * @see String
     */
    fun showPro(message: String) {
        (activity as BaseActivity).showProgress(message)
    }

    /**
     * hide the progress view
     */
    fun hidePro() {
        (activity as BaseActivity).hideProgress()
    }

    /**
     * pops the front fragment from screen and take to previous added
     * if present any
     */
    fun backPress() {
        (activity as BaseActivity).supportFragmentManager.popBackStack()
    }

    /**
     * opens a activity with back stack
     * @see Class
     */
    fun <T> openActivity(javaClass: Class<T>) {
        (activity as BaseActivity).openActivity(javaClass)
    }

    /**
     * opens a activity with no back stack
     * @see Class
     */
    fun <T> openNewActivity(javaClass: Class<T>) {
        (activity as BaseActivity).openNewActivity(javaClass)
    }

}