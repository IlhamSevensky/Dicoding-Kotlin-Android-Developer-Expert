package com.ilham.made.firstsubmission.ui

import android.view.View
import com.ilham.made.firstsubmission.R
import com.ilham.made.firstsubmission.activity.MainActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout

class MainActivityUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            recyclerView {
                id = R.id.rv_liga
                lparams(width = matchParent, height = matchParent)
            }
        }
    }
}