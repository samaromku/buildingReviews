package ru.andrey.savchenko.buildingreviews.base
import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.progress_error.*
import ru.andrey.savchenko.buildingreviews.fragments.ErrorFragment

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

    override fun showError(error: String, repeat:() -> Unit){
        fragmentManager.beginTransaction()
                .replace(ru.andrey.savchenko.buildingreviews.R.id.container, ErrorFragment())
                .commit()

        llProgressError.visibility = View.VISIBLE
        tvErrorBody.text = error
        btnRepeat.setOnClickListener {
            llProgressError.visibility = View.GONE
            repeat()
        }
//        val builder = AlertDialog.Builder(activity, ru.andrey.savchenko.buildingreviews.R.style.MyDialogTheme)
//        builder.setTitle("Ошибка")
//                .setMessage(error)
//                .setCancelable(false)
//                .setPositiveButton("ОК", { dialog, _ -> dialog.dismiss() })
//                .setNegativeButton("Поторить", { dialog, _ ->
//                    dialog.dismiss()
//                    repeat()
//                })
//                .setNeutralButton("Войти", {dialog, _ -> dialog.dismiss()})
//        errordialog = builder.create()
//        errordialog.show()
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
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showDialog() {
        dialog.show()
    }

}