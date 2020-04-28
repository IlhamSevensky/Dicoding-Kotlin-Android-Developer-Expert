package com.ilham.made.lastsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.model.StandingsModel
import com.ilham.made.lastsubmission.utils.Helper.clearImageView
import com.ilham.made.lastsubmission.utils.Helper.setImageToView
import kotlinx.android.synthetic.main.item_row_standings.view.*

class StandingAdapter(private var teams: MutableList<StandingsModel>) :
    RecyclerView.Adapter<StandingAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: StandingsModel) {
            with(itemView) {
                with(team) {
                    val position = adapterPosition + 1
                    tv_standings_position.text = position.toString()
                    tv_standings_team.text = name
                    tv_standings_played.text = played.toString()
                    tv_standings_win.text = win.toString()
                    tv_standings_draw.text = draw.toString()
                    tv_standings_loss.text = loss.toString()
                    tv_standings_goal_plus_minus.text =
                        resources.getString(R.string.goal_plus_minus, goalsFor, goalsAgainst)
                    tv_standings_goal_difference.text = goalsDifference.toString()
                    tv_standings_point.text = total.toString()

                    img_standings_team.setImageResource(0)
                    if (!teamBadge.isNullOrEmpty()) {
                        clearImageView(context, img_standings_team)
                        setImageToView(
                            context,
                            teamBadge!!,
                            img_standings_team
                        )
                    } else {
                        img_standings_team.setImageResource(R.drawable.no_image_available)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_standings, parent, false
            )
        )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    fun setTeams(data: List<StandingsModel>) {
        teams.clear()
        teams.addAll(data)
        notifyDataSetChanged()
    }
}