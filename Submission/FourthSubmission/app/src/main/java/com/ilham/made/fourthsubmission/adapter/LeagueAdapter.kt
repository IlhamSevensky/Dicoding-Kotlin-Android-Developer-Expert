package com.ilham.made.fourthsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.fourthsubmission.R
import com.ilham.made.fourthsubmission.model.LeagueModel
import com.ilham.made.fourthsubmission.utils.Helper
import kotlinx.android.synthetic.main.item_row_league.view.*

class LeagueAdapter(private var leagues: MutableList<LeagueModel>) :
    RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    private var onLeagueClickCallback: LeagueClickCallback? = null

    fun setOnLeagueClickCallback(onLeagueClickCallback: LeagueClickCallback) {
        this.onLeagueClickCallback = onLeagueClickCallback
    }

    interface LeagueClickCallback {
        fun onItemClicked(data: LeagueModel)
    }

    inner class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(league: LeagueModel) {
            with(itemView) {
                with(league){
                    tv_league_id.text = idLeague.toString()
                    tv_league_name.text = strLeague
                    if (strBadge != null) {
                        Helper.setImageToView(context, strBadge!!, img_league)
                    }
                }

                setOnClickListener {
                    onLeagueClickCallback?.onItemClicked(league)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder =
        LeagueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_league, parent, false
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