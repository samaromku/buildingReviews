package ru.andrey.savchenko.buildingreviews.activities.registry

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_registry.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.auth.AuthActivity
import ru.andrey.savchenko.buildingreviews.activities.search.SearchActivity
import ru.andrey.savchenko.buildingreviews.base.BaseActivity

/**
 * Created by savchenko on 16.04.18.
 */
class RegistryActivity : BaseActivity(), RegistryView {
    @InjectPresenter
    lateinit var presenter: RegistryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        changeToolbarTitle(getString(R.string.registry))
        btnRegister.setOnClickListener {
            presenter.registerUser(etLogin.text.toString(),
                    etPassword.text.toString(),
                    etName.text.toString(),
                    etEmail.text.toString())
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun startCompaniesActivity() {
        startActivity(Intent(this, SearchActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }
}