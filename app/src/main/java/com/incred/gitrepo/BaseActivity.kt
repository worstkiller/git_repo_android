package com.incred.gitrepo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.incred.gitrepo.utils.EXTRA_BUNDLE_DATA
import com.incred.gitrepo.widget.ProgressDialog

/**
 * base activity class for ui components with basic functionality
 */
open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
    }

    /**
     * this will finish the current activity and start a new one
     * @see Class<T>
     * @param javaClass
     * @return Unit
     */
    internal fun <T> openNewActivity(javaClass: Class<T>) {
        val intent = Intent(this, javaClass)
        startActivity(intent)
        finish()
    }

    /**
     * this just simply starts a new activity back stack is maintained
     * for previous one
     * @see Class<T>
     * @param javaClass
     * @return Unit
     */
    internal fun <T> openActivity(javaClass: Class<T>) {
        val intent = Intent(this, javaClass)
        startActivity(intent)
    }

    /**
     * this just simply starts a new activity back stack is maintained
     * for previous one and bundle is attached
     * caller should use <p>EXTRA_BUNDLE_DATA</p> to get bundle
     * @see Class<T>
     * @param javaClass
     * @param bundle
     * @return Unit
     */
    internal fun <T> openActivity(javaClass: Class<T>, bundle: Bundle) {
        val intent = Intent(this, javaClass)
        intent.putExtra(EXTRA_BUNDLE_DATA, bundle)
        startActivity(intent)
    }

    /**
     * call this method to replace a fragment with fragment type base and rest is handled
     * under the hood
     * @see Class<FragmentManager>
     * @param containerId
     * @param fragment
     * @param isBackStack
     */
    open fun replaceFragment(containerId: Int, fragment: BaseFragment, isBackStack: Boolean) {
        if (isBackStack) {
            supportFragmentManager.replaceFragmentWithBackStack(containerId, fragment)
        } else {
            supportFragmentManager.replaceFragment(containerId, fragment)
        }
    }

    /**
     * replaces the fragment using fragment manager extension function, with no back stack
     * @param containerId
     * @param fragment
     */
    private fun FragmentManager.replaceFragment(containerId: Int, fragment: BaseFragment) {
        beginTransaction().replace(containerId, fragment, fragment.javaClass.canonicalName).commit()
    }

    /**
     * replaces the fragment using fragment manager extension function, with back stack
     * @param containerId
     * @param fragment
     */
    private fun FragmentManager.replaceFragmentWithBackStack(containerId: Int, fragment: BaseFragment) {
        beginTransaction()
                .replace(containerId, fragment, fragment.javaClass.canonicalName)
                .addToBackStack(fragment::class.java.canonicalName)
                .commit()
    }

    /**
     * set the message and show the progress
     * @param message
     */
    fun showProgress(message: String) {
        progressDialog.setMessages(message)
        progressDialog.show()
    }

    /**
     * hide the progress view
     */
    fun hideProgress() {
        progressDialog.hide()
    }

    /**
     * does nothing ,to use child class must provide a working implementation
     * with fragments, when called with fragment passed in it will call the respective
     * activity's overridden changeView using
     */
    open fun changeView(fragment: BaseFragment, isBackStack: Boolean) = Unit

    /**
     * show the message passed as
     * @see String
     * @param message
     */
    fun showToast(message: String) {
        Toast.makeText(BaseActivity@ this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        progressDialog.dismiss()
    }
}
