package com.ilham.made.firstsubmission.ui

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.ilham.made.firstsubmission.R
import com.ilham.made.firstsubmission.activity.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class DetailActivityUI : AnkoComponent<DetailActivity> {

    override fun createView(ui: AnkoContext<DetailActivity>): View = with(ui){
        scrollView {
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                backgroundColor = Color.WHITE

                imageView {
                    id = R.id.img_detail_liga
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    padding = dip(5)
                }.lparams(width = matchParent,height = dip(300))

                cardView {
                    backgroundColor = Color.WHITE
                    radius = 10F

                    textView {
                        id = R.id.tv_detail_liga_name
                        textSize = 20F
                        textColor = Color.BLACK
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        maxLines = 2
                        ellipsize = TextUtils.TruncateAt.END

                    }.lparams(width = matchParent, height = wrapContent){
                        margin = dip(16)
                    }

                }.lparams(width = matchParent, height = wrapContent)

                textView {
                    id = R.id.tv_detail_liga_overview
                    textSize = 18F
                    textColor = Color.DKGRAY

                }.lparams(width = matchParent, height = matchParent){
                    margin = dip(10)
                }
            }
        }
    }

}