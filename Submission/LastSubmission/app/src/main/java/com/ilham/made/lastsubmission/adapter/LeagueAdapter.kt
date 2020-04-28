package com.ilham.made.lastsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.model.LeagueModel
import com.ilham.made.lastsubmission.utils.Helper.clearImageView
import com.ilham.made.lastsubmission.utils.Helper.setImageToView
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

                    img_league.setImageResource(0)
                    if (strBadge != null) {
                        clearImageView(context, img_league)
                        setImageToView(context, strBadge!!, img_league)
                    }else {
                        img_league.setImageResource(R.drawable.no_image_available)
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