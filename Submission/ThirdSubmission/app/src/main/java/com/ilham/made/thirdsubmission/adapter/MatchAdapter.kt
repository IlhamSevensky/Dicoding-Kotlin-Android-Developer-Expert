package com.ilham.made.thirdsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.thirdsubmission.R
import com.ilham.made.thirdsubmission.model.MatchModel
import com.ilham.made.thirdsubmission.utils.Helper
import com.ilham.made.thirdsubmission.utils.Helper.setImageToView
import kotlinx.android.synthetic.main.item_row_match.view.*

class MatchAdapter(private var matches: MutableList<MatchModel>) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private var onMatchClickCallback: MatchClickCallback? = null

    fun setMatchClickCallback(onMatchClickCallback: MatchClickCallback) {
        this.onMatchClickCallback = onMatchClickCallback
    }

    interface MatchClickCallback {
        fun onItemClicked(data: MatchModel)
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(match: MatchModel) {
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
                        setImageToView(
                            context,
                            strBadgeHomeTeam!!,
                            img_match_home_team
                        )
                    } else {
                        img_match_home_team.setBackgroundResource(R.drawable.no_image_available)
                    }

                    if (!strBadgeAwayTeam.isNullOrEmpty()) {
                        setImageToView(
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

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    fun setMatch(data: List<MatchModel>) {
        matches.clear()
        matches.addAll(data)
        notifyDataSetChanged()
    }

}