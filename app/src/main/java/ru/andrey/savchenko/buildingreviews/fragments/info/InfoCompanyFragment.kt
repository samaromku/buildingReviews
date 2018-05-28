package ru.andrey.savchenko.buildingreviews.fragments.info

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.storage.Const
import ru.andrey.savchenko.buildingreviews.storage.Utils.Companion.getImageFullUrl

/**
 * Created by savchenko on 11.04.18.
 */
class InfoCompanyFragment : BaseFragment(), InfoView {
    lateinit var presenter: InfoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewModelProviders.of(this).get(InfoPresenter::class.java)
        activity?.intent?.getIntExtra(Const.COMPANY_ID, 0)?.let {
            presenter.getInfoCompany(it)
        }
        presenter.company.observe(this, Observer { company ->
            if (company != null) {
                setToolbarText(company.title)
                setSite(company.siteUrl)
                setDescription(company.description)
                setPhone(company.phone)
                setAddress(company.address)
                setLogo(company.imageUrl)
            }
        })
    }

    override fun setToolbarText(text: String) {
        setToolbarTitle(text)
    }

    override fun setLogo(text: String) {
        Picasso.get()
                .load(getImageFullUrl(text))
                .into(ivLogo)
    }

    override fun setPhone(text: String) {
        tvPhone.text = text
    }

    override fun setAddress(text: String) {
        tvAddress.text = text
    }

    override fun setSite(text: String) {
        tvSiteUrl.text = text
    }

    override fun setDescription(text: String) {
        tvDescription.text = text
    }
}