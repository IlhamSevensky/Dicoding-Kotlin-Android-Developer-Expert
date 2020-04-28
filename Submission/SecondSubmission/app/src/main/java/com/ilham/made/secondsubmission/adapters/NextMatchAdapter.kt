package com.ilham.made.secondsubmission.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.secondsubmission.R
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.utils.Helper
import com.ilham.made.secondsubmission.utils.Helper.setImage
import kotlinx.android.synthetic.main.item_row_match.view.*

class NextMatchAdapter(private var matches: MutableList<MatchModel>) :
    RecyclerView.Adapter<NextMatchAdapter.NextMatchViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MatchModel)
    }

    inner class NextMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(match: MatchModel) {
            with(itemView) {

                tv_match_round.text = if (match.intRound != null) {
                    resources.getString(R.string.match_round, match.intRound.toString())
                } else {
                    resources.getString(R.string.empty_data)
                }

                tv_match_home_team.text =
                    match.strHomeTeam ?: resources.getString(R.string.empty_data)

                tv_match_away_team.text =
                    match.strAwayTeam ?: resources.getString(R.string.empty_data)

                tv_match_home_score.text = if (match.intHomeScore != null) {
                    match.intHomeScore.toString()
                } else {
                    resources.getString(R.string.empty_data)
                }

                tv_match_away_score.text = if (match.intAwayScore != null) {
                    match.intAwayScore.toString()
                } else {
                    resources.getString(R.string.empty_data)
                }

                if (match.strBadgeHomeTeam != null) {
                    setImage(
                        itemView.context,
                        match.strBadgeHomeTeam,
                        img_match_home_team
                    )
                } else {
                    img_match_home_team.setBackgroundResource(R.drawable.no_image_available)
                }

                if (match.strBadgeAwayTeam != null) {
                    setImage(
                        itemView.context,
                        match.strBadgeAwayTeam,
                        img_match_away_team
                    )
                } else {
                    img_match_away_team.setBackgroundResource(R.drawable.no_image_available)
                }

                tv_match_schedule.text =
                    Helper.changeDateFormat(
                        match.dateEvent,
                        match.strTime
                    )

                setOnClickListener {
                    onItemClickCallback?.onItemClicked(match)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder =
        NextMatchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_match, parent, false
            )
        )

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    fun setMatch(data: List<MatchModel>) {
        matches.clear()
        matches.addAll(data)
        notifyDataSetChanged()
    }

}