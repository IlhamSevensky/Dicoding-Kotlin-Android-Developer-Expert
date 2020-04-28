package com.ilham.made.firstsubmission.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.ilham.made.firstsubmission.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class LigaItemUI : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)

            cardView {
                radius = 10F

                verticalLayout {
                    padding = dip(10)
                    gravity = Gravity.CENTER

                    imageView {
                        id = R.id.img_liga
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                    }.lparams(width = dip(100), height = dip(100)){
                        bottomMargin = dip(5)
                    }

                    textView {
                        id = R.id.tv_liga_name
                        textSize = 16f
                        textColor = Color.BLACK
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = matchParent, height = wrapContent) {
                        margin = dip(5)
                    }

                }.lparams(width = matchParent, height = matchParent)

            }.lparams(width = matchParent, height = matchParent){
                margin = dip(10)
                padding = dip(5)
            }
        }
    }
}