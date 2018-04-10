package ru.andrey.savchenko.buildingreviews.base
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by savchenko on 18.02.18.
 */
class BaseFragment : Fragment(){

    protected fun setToolbarTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

}