package com.ilham.made.firstsubmission.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.firstsubmission.R
import com.ilham.made.firstsubmission.model.LigaModel
import com.ilham.made.firstsubmission.ui.LigaItemUI
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class LigaAdapter(
    private val context: Context,
    private val ligaItems: List<LigaModel>,
    private val listener: (LigaModel) -> Unit
) :
    RecyclerView.Adapter<LigaAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        private val tvName: TextView = containerView.find(R.id.tv_liga_name)
        private val imgLiga: ImageView = containerView.find(R.id.img_liga)

        fun bindItem(items: LigaModel, listener: (LigaModel) -> Unit) {

            tvName.text = items.name
            items.image?.let { Picasso.get().load(it).into(imgLiga) }

            itemView.setOnClickListener {
                listener(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LigaItemUI().createView(
            AnkoContext.create(parent.context, context)
        )
    )

    override fun getItemCount(): Int = ligaItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(ligaItems[position], listener)
    }
}