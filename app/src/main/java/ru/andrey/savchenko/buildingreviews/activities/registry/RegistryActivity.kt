package ru.andrey.savchenko.buildingreviews.activities.registry

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_registry.*
import ru.andrey.savchenko.buildingreviews.R
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
        setContentView(R.layout.activity_registry)
        btnRegister.setOnClickListener {
            presenter.registerUser(etLogin.text.toString(),
                    etPassword.text.toString(),
                    etName.text.toString())
        }
    }

    override fun startCompaniesActivity() {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}