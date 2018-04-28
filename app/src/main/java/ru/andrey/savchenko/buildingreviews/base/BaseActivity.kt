package ru.andrey.savchenko.buildingreviews.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.fragments.ErrorFragment
import ru.andrey.savchenko.buildingreviews.fragments.ProgressFragment


/**
 * Created by savchenko on 13.02.18.
 */
@SuppressLint("Registered")
open class BaseActivity : MvpAppCompatActivity(),
        BaseView,
        BaseViewMethods {
    lateinit var errordialog: AlertDialog
    private var mBottomSheetDialog: BottomSheetDialog? = null
    lateinit var dialog: ProgressDialog


    protected fun initBackButton() {
        val upArrow = resources.getDrawable(R.drawable.ic_arrow_back)
        upArrow.setColorFilter(resources.getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialogTitleAndText("Загрузка данных", "Ожидайте")
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showDialog() {
        showProgressFragment(supportFragmentManager)
    }

    override fun hideDialog() {
        hideProgressFragment(supportFragmentManager)
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

    override fun showError(error: String, repeat: () -> Unit) {
        val errorFragment = ErrorFragment()
        errorFragment.error = error
        errorFragment.repeat = {
            supportFragmentManager.beginTransaction()
                    .remove(supportFragmentManager.findFragmentByTag("error"))
                    .commit()
            repeat()
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, errorFragment, "error")
                .commit()

        //bottomSheet
//        mBottomSheetDialog = BottomSheetDialog(this)
//        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
//        val tvError = sheetView.findViewById<TextView>(R.id.tvErrorBody)
//        tvError.text = error
//
//        sheetView.findViewById<LinearLayout>(R.id.llOk).setOnClickListener {
//            mBottomSheetDialog?.dismiss()
//        }
//        sheetView.findViewById<LinearLayout>(R.id.llRepeat).setOnClickListener {
//            mBottomSheetDialog?.dismiss()
//            repeat()
//        }
//        sheetView.findViewById<LinearLayout>(R.id.llAuth).setOnClickListener {
//            mBottomSheetDialog?.dismiss()
//            startActivity(Intent(this, AuthActivity::class.java))
//        }
//
//        if (error.contains("403")) {
//            llAuth.visibility = View.VISIBLE
//        }
//
//        if (mBottomSheetDialog != null && !mBottomSheetDialog!!.isShowing) {
//
//            mBottomSheetDialog?.setContentView(sheetView)
//
//            mBottomSheetDialog?.show()
//
//        }
        //dimple errorDialog
//        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
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