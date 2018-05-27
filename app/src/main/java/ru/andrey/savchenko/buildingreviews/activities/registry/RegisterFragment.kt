package ru.andrey.savchenko.buildingreviews.activities.registry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_register.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.search.SearchActivity
import ru.andrey.savchenko.buildingreviews.base.BaseFragment

/**
 * Created by savchenko on 27.05.18.
 */
class RegisterFragment:BaseFragment(), RegistryView {
    @InjectPresenter
    lateinit var presenter: RegistryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener {
            presenter.registerUser(etLogin.text.toString(),
                    etPassword.text.toString(),
                    etName.text.toString(),
                    etEmail.text.toString())
        }
    }

    override fun startCompaniesActivity() {
        startActivity(Intent(activity, SearchActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.finish()
    }
}