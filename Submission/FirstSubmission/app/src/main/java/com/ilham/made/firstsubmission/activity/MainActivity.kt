package com.ilham.made.firstsubmission.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.firstsubmission.R
import com.ilham.made.firstsubmission.adapter.LigaAdapter
import com.ilham.made.firstsubmission.model.LigaModel
import com.ilham.made.firstsubmission.ui.MainActivityUI
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var rvLiga: RecyclerView
    private var ligaItems: MutableList<LigaModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        initializeUI()

        initData()

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        rvLiga.let {
            it.layoutManager = GridLayoutManager(this, 2)
            it.adapter = LigaAdapter(this, ligaItems) { data ->
                startActivity<DetailActivity>(DetailActivity.EXTRA_DATA to data)
            }
        }
    }

    private fun initializeUI() {
        rvLiga = find(R.id.rv_liga)
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.liga_name)
        val image = resources.obtainTypedArray(R.array.liga_image)
        val overview = resources.getStringArray(R.array.liga_overview)
        ligaItems.clear()
        for (i in name.indices) {
            ligaItems.add(
                LigaModel(
                    name[i],
                    image.getResourceId(i, 0),
                    overview[i]
                )
            )
        }

        //Recycle the typed array
        image.recycle()
    }
}
