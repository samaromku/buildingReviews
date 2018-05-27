package ru.andrey.savchenko.buildingreviews.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_error.*
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.auth.AuthActivity
import ru.andrey.savchenko.buildingreviews.activities.onecompany.ADD_REVIEW
import ru.andrey.savchenko.buildingreviews.activities.onecompany.AUTH
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by savchenko on 28.04.18.
 */
class ErrorFragment : BaseFragment() {
//    lateinit var error: String
//    lateinit var repeat: () -> Unit
    lateinit var errorRepeat: ErrorRepeat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvErrorBody.text = errorRepeat.error.message
        btnRepeat.setOnClickListener {
            llProgressError.visibility = View.GONE
            errorRepeat.repeat()
        }
        btnAuth.setOnClickListener {
            App.cicerone.router.navigateTo(AUTH, ADD_REVIEW)
//            startActivity(Intent(activity, AuthActivity::class.java))
        }
        //auth failed
        if (errorRepeat.error.code==403) {
            btnAuth.visible()
        }
    }
}