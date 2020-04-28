package com.ilham.made.firstsubmission.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ilham.made.firstsubmission.R
import com.ilham.made.firstsubmission.model.LigaModel
import com.ilham.made.firstsubmission.ui.DetailActivityUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var tvDetailLigaName: TextView
    private lateinit var tvDetailLigaOverview: TextView
    private lateinit var imgDetailLiga: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailActivityUI().setContentView(this)
        val data = intent.getParcelableExtra<LigaModel>(EXTRA_DATA)

        initializeUI()

        tvDetailLigaName.text = data?.name
        tvDetailLigaOverview.text = data?.overview
        data?.image?.let {
            Picasso.get().load(it).into(imgDetailLiga)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initializeUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tvDetailLigaName = find(R.id.tv_detail_liga_name)
        tvDetailLigaOverview = find(R.id.tv_detail_liga_overview)
        imgDetailLiga = find(R.id.img_detail_liga)
    }



}