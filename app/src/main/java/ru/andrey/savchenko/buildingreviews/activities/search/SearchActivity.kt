package ru.andrey.savchenko.buildingreviews.activities.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.view.CircleTransform
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyActivity
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler


class SearchActivity : BaseActivity(), SearchView, OnItemClickListener {
    val TAG = SearchActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        val list = mutableListOf(
                Company(1, "Неометрия",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/6df/183_183_1/neo_logo_1_.png",
                        "Проекты строительной компании Неометрия. Жилые комплексы «Синема» и «Ежи» возводятся в крайне удачном месте: в непосредственном соседстве с лоном природы и в тоже время – не в отрыве от цивилизации. Покупкой квартиры в одном из ЖК будут удовлетворены как любители тишины и умиротворяющих видов с балкона, так и те, кому важно слышать оживленный ритм города. Фактор, который еще больше сближает потенциальных покупателей – привлекательные условия оплаты и цена на квартиры застройщика Неометрия. Стать владельцем заветных ключей можно, расплатившись по наличному или безналичному расчету, в рассрочку или в ипотеку. "),
                Company(2,
                        "Черноморская финансовая компания",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/189/183_183_1/189fb50a73687ed9f03777860becf3a3.png", ""),
                Company(3,
                        "Онис",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/4c5/183_183_1/4234234.jpg", ""),
                Company(4,
                        "Нефтестройиндустрия-Юг ",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/739/183_183_1/107.jpg", ""),
                Company(5,
                        "test",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/f0a/183_183_1/logo_324_1_.png", ""),
                Company(6,
                        "test",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/944/183_183_1/logo_2_.png", ""),
                Company(7,
                        "test",
                        "https://novostroi-ki.ru/upload/resize_cache/iblock/ba1/183_183_1/eafaf82340634f666af3c6f9dd970bc9_1_.jpg", "")
        )
        val adapter = SearchAdapter(list,
                this as OnItemClickListener)
        rvCompanies.layoutManager = LinearLayoutManager(this)
        rvCompanies.adapter = adapter
        ivBack.setOnClickListener { backClick() }

//        NetworkHandler.getService().getPosts()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({response -> println("response $response")},
//                        {t -> t.printStackTrace()})
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_search -> {openToolbarSearch()}

        }
        return super.onOptionsItemSelected(item)
    }

    private fun openToolbarSearch() {
        toolbar.visibility = View.GONE
        search_toolbar.visibility = View.VISIBLE
        showKeyboard(this, etSearch)
    }

    override fun onclick(position: Int) {
        startActivity(Intent(this, OneCompanyActivity::class.java))
    }

    private fun backClick() {
        toolbar.visibility = View.VISIBLE
        search_toolbar.setVisibility(View.GONE)
        hideKeyboard(this, etSearch)
    }

    override fun onBackPressed() {
        if (search_toolbar.visibility == View.VISIBLE) {
            toolbar.visibility = View.VISIBLE
            search_toolbar.visibility = View.GONE
            hideKeyboard(this, findViewById(R.id.etSearch))
        }else {
            super.onBackPressed()
        }
    }
}
