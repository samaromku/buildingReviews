package ru.andrey.savchenko.buildingreviews.base
import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by savchenko on 18.02.18.
 */
open class BaseFragment : MvpAppCompatFragment(),BaseView{
    lateinit var dialog: ProgressDialog
    lateinit var errordialog: AlertDialog

    protected fun setToolbarTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

    override fun showError(error: String){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Ошибка")
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton("ОК", { dialog, _ -> dialog.dismiss() })
        errordialog = builder.create()
        errordialog.setOnShowListener {
            errordialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.background_dark))
            errordialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.background_dark))
        }
        errordialog.show()
    }

    override fun changeToolbarTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        println("actionBar $actionBar")
        if (actionBar != null)
            actionBar.title = title
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogTitleAndText("Загрузка данных", "Ожидайте")
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

    override fun hideDialog() {
        dialog.dismiss()
    }

    override fun showProgress() {
//        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
//        progressBar.visibility = View.GONE
    }

    override fun showDialog() {
        dialog.show()
    }

}