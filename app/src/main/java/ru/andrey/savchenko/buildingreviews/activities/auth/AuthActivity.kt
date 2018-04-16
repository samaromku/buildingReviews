package ru.andrey.savchenko.buildingreviews.activities.auth

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_auth.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.search.SearchActivity
import ru.andrey.savchenko.buildingreviews.base.BaseActivity

/**
 * Created by savchenko on 16.04.18.
 */
class AuthActivity:BaseActivity(), AuthView {
    @InjectPresenter
lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setDialogTitleAndText("wait", "loading")
        btnEnter.setOnClickListener { presenter.auth(etLogin.text.toString(), etPassword.text.toString()) }
    }

    override fun startCompaniesActivity() {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}