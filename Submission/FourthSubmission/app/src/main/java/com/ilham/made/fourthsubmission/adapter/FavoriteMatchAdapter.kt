package com.ilham.made.fourthsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.fourthsubmission.R
import com.ilham.made.fourthsubmission.model.MatchFavoriteModel
import com.ilham.made.fourthsubmission.utils.Helper
import kotlinx.android.synthetic.main.item_row_match.view.*

class FavoriteMatchAdapter(private var matches: MutableList<MatchFavoriteModel>) :
    RecyclerView.Adapter<FavoriteMatchAdapter.MatchViewHolder>() {

    private var onMatchClickCallback: MatchClickCallback? = null

    fun setMatchClickCallback(onMatchClickCallback: MatchClickCallback) {
        this.onMatchClickCallback = onMatchClickCallback
    }

    interface MatchClickCallback {
        fun onItemClicked(data: MatchFavoriteModel)
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(match: MatchFavoriteModel) {
            with(itemView) {
                with(match) {

                    tv_match_round.text = if (!intRound?.toString().isNullOrEmpty()) {
                        resources.getString(R.string.match_round, intRound.toString())
                    } else {
                        resources.getString(R.string.empty_data)
                    }

                    tv_match_home_team.text =
                        strHomeTeam ?: resources.getString(R.string.empty_data)

                    tv_match_away_team.text =
                        strAwayTeam ?: resources.getString(R.string.empty_data)


                    tv_match_home_score.text =
                        intHomeScore?.toString() ?: resources.getString(R.string.empty_data)

                    tv_match_away_score.text =
                        intAwayScore?.toString() ?: resources.getString(R.string.empty_data)

                    if (!strBadgeHomeTeam.isNullOrEmpty()) {
                        Helper.setImageToView(
                            context,
                            strBadgeHomeTeam!!,
                            img_match_home_team
                        )
                    } else {
                        img_match_home_team.setBackgroundResource(R.drawable.no_image_available)
                    }

                    if (!strBadgeAwayTeam.isNullOrEmpty()) {
                        Helper.setImageToView(
                            context,
                            strBadgeAwayTeam!!,
                            img_match_away_team
                        )
                    } else {
                        img_match_away_team.setBackgroundResource(R.drawable.no_image_available)
                    }

                    tv_match_schedule.text =
                        Helper.parseDateTimeFormat(
                            dateEvent,
                            strTime
                        )
                }

                setOnClickListener {
                    onMatchClickCallback?.onItemClicked(match)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder =
        MatchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_match, parent, false
            )
        )

    override fun getItemCount(): Int = matches.size

    fun setMatch(data: List<MatchFavoriteModel>) {
        matches.clear()
        matches.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position])

    }

}