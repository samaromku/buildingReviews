package ru.andrey.savchenko.buildingreviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.progress_error.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment

/**
 * Created by savchenko on 28.04.18.
 */
class ErrorFragment:BaseFragment() {
    lateinit var error:String
    lateinit var repeat: () -> Unit


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.progress_error, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvErrorBody.text = error
        btnRepeat.setOnClickListener {
            llProgressError.visibility = View.GONE
            repeat()
        }
    }

}