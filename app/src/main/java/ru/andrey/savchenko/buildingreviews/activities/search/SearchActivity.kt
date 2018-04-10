package ru.andrey.savchenko.buildingreviews.activities.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.view.CircleTransform

class SearchActivity : BaseActivity(), SearchView, OnItemClickListener {
    val TAG = SearchActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val list = mutableListOf(
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/6df/183_183_1/neo_logo_1_.png", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/189/183_183_1/189fb50a73687ed9f03777860becf3a3.png", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/4c5/183_183_1/4234234.jpg", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/739/183_183_1/107.jpg", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/f0a/183_183_1/logo_324_1_.png", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/944/183_183_1/logo_2_.png", ""),
                Company(1, "test", "https://novostroi-ki.ru/upload/resize_cache/iblock/ba1/183_183_1/eafaf82340634f666af3c6f9dd970bc9_1_.jpg", "")
        )
        val adapter = SearchAdapter(list,
                this as OnItemClickListener)
        rvCompanies.layoutManager = LinearLayoutManager(this)
        rvCompanies.adapter = adapter
    }

    override fun onclick(position: Int) {
        Log.i(TAG, position.toString())
    }
}
