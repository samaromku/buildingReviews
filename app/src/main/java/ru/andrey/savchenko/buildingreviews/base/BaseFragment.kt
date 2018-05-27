package ru.andrey.savchenko.buildingreviews.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.labo.kaji.fragmentanimations.CubeAnimation
import com.labo.kaji.fragmentanimations.FlipAnimation
import kotlinx.android.synthetic.main.fragment_reviews.*
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.storage.gone
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by savchenko on 18.02.18.
 */
open class BaseFragment : MvpAppCompatFragment(),
        BaseView,
        BaseViewMethods {
    lateinit var dialog: ProgressDialog
    lateinit var errordialog: AlertDialog

//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
//        return FlipAnimation.create(FlipAnimation.LEFT, enter, 300)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogTitleAndText("Загрузка данных", "Ожидайте")
    }

    protected fun setToolbarTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        activity?.supportFragmentManager?.let { showErrorFragmentError(error, repeat, it) }
//        val errorFragment = ErrorFragment()
//        errorFragment.error = "Код: ${error.code} \n Ошибка: ${error.message}"
//        errorFragment.repeat = {
//            activity.supportFragmentManager.beginTransaction()
//                    .remove(activity.supportFragmentManager.findFragmentByTag("error"))
//                    .commit()
//            repeat()
//        }
//        activity.supportFragmentManager.beginTransaction()
//                .add(R.id.container, errorFragment, "error")
//                .commit()
    }

    override fun changeToolbarTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        println("actionBar $actionBar")
        if (actionBar != null)
            actionBar.title = title
    }


    protected fun setDialogTitleAndText(title: String, message: String) {
        dialog = ProgressDialog(activity)
        dialog.setCancelable(false)
        dialog.setMessage(message)
        dialog.setTitle(title)
    }

    override fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }


    override fun showProgress() {
        progressBar.visible()
    }

    override fun hideProgress() {
        progressBar.gone()
    }

    override fun showDialog() {
        activity?.supportFragmentManager?.let { showProgressFragment(it) }
    }

    override fun hideDialog() {
        activity?.supportFragmentManager?.let { hideProgressFragment(it) }
    }


}