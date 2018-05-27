package ru.andrey.savchenko.buildingreviews.activities.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_login.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.registry.RegistryActivity
import ru.andrey.savchenko.buildingreviews.activities.search.SearchActivity
import ru.andrey.savchenko.buildingreviews.base.BaseFragment

/**
 * Created by savchenko on 27.05.18.
 */
class AuthFragment: BaseFragment(), AuthView {
    @InjectPresenter
    lateinit var presenter: AuthPresenter
    lateinit var key:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkAuthStart()
        btnRegister.setOnClickListener {
            startActivity(Intent(activity, RegistryActivity::class.java))
            activity?.finish()
        }
        btnEnter.setOnClickListener {
            presenter.auth(etLogin.text.toString(), etPassword.text.toString(), key)
        }
    }

    override fun startCompaniesActivity() {
        startActivity(Intent(activity, SearchActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.finish()
    }
}