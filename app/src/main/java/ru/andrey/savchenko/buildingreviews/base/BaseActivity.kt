package ru.andrey.savchenko.buildingreviews.base

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.AppCompatSpinner
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity


/**
 * Created by savchenko on 13.02.18.
 */
@SuppressLint("Registered")
open class BaseActivity : MvpAppCompatActivity(), BaseView {
    lateinit var errordialog: AlertDialog
    lateinit var dialog: ProgressDialog

    protected fun initBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialogTitleAndText("Загрузка данных", "Ожидайте")
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

    protected fun setDialogTitleAndText(title: String, message: String) {
        dialog = ProgressDialog(this)
        dialog.setCancelable(false)
        dialog.setMessage(message)
        dialog.setTitle(title)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun hideDialog() {
        dialog.dismiss()
    }

    override fun changeToolbarTitle(title: String) {
        val actionBar = supportActionBar
        println("actionBar $actionBar")
        if (actionBar != null)
            actionBar.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showError(error: String){
        val builder = AlertDialog.Builder(this)
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

//    override fun showError(error: String, okEvent: () -> Unit?) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Ошибка")
//                .setMessage(error)
//                .setCancelable(false)
//                .setPositiveButton("ОК", { dialog, _ ->
//                    okEvent.invoke()
//                    dialog.dismiss()
//                })
//        errordialog = builder.create()
//        errordialog.setOnShowListener {
//            errordialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.colorPrimaryDark))
//            errordialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.colorPrimaryDark))
//        }
//        errordialog.show()
//    }

    fun showConfirmDialog(title: String = "",
                          message: String = "",
                          positive: () -> Unit,
                          negative: (() -> Unit)? = null)  {
        showNotCancelableDialog(this,
                title = "Внимание! Удалить документ?",
                positiveClick = DialogInterface.OnClickListener { dialog, _ ->
                    positive.invoke()
                },
                negativeClick = DialogInterface.OnClickListener { dialog, _ ->
                    negative?.invoke()
                })
    }

    fun showNotCancelableDialog(context: Context,
                                title: String = "",
                                message: String = "",
                                positiveClick: DialogInterface.OnClickListener,
                                negativeClick: DialogInterface.OnClickListener) {
        val dialog = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Да", positiveClick)
                .setNegativeButton("Нет", negativeClick).create()
        dialog.setOnShowListener {
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.background_dark))
            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.background_dark))
        }
        dialog.show()
    }

    fun addListToSpinner(context: Context,
                         spinnerList:List<String>,
                         spinner: AppCompatSpinner,
                         setSelectedNothing:Boolean=false){
        val adapter = ArrayAdapter<String>(context,
                R.layout.simple_spinner_item, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        if(!setSelectedNothing) {
            spinner.setSelection(0)
        }
    }

    fun hideKeyboard(context: Context, editText: EditText) {
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showKeyboard(context: Context, editText: EditText) {
        editText.requestFocus()
        val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(editText, 0)
    }
}