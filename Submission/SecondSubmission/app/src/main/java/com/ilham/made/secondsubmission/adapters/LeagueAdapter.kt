package com.ilham.made.secondsubmission.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.models.LeagueModel
import kotlinx.android.synthetic.main.item_row_grid.view.*

class LeagueAdapter(private var leagues: MutableList<LeagueModel>) :
    RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: LeagueModel)
    }

    inner class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(league: LeagueModel) {
            with(itemView) {
                tv_league_id.text = league.idLeague.toString()
                tv_league_name.text = league.strLeague
                Glide.with(context).load(league.strBadge).into(img_main_league)

                setOnClickListener {
                    onItemClickCallback?.onItemClicked(league)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder =
        LeagueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_grid, parent, false
            )
        )

    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(leagues[position])
    }

    fun setLeagues(data: List<LeagueModel>) {
        leagues.clear()
        leagues.addAll(data)
        notifyDataSetChanged()
    }
}