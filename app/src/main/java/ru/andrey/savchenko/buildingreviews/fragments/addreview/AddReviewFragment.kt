package ru.andrey.savchenko.buildingreviews.fragments.addreview

import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_add_review.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.storage.Const

/**
 * Created by savchenko on 11.04.18.
 */
class AddReviewFragment:BaseFragment(), AddReviewView {
    @InjectPresenter
    lateinit var presenter: AddReviewPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_review, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setDialogTitleAndText("wait", "loading")
        rbRating.setOnRatingBarChangeListener { ratingBar, fl, b ->
            tvRating.visibility = View.VISIBLE
            tvRating.text = "$fl из 5" }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.send_review, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_send -> {presenter.addReview(
                    activity.intent.getIntExtra(Const.COMPANY_ID, 0),
                    rbRating.numStars,
                    etPositive.text.toString(),
                    etNegative.text.toString(),
                    etGeneral.text.toString()
            )}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finishAfterSent() {
        activity.finish()
    }
}