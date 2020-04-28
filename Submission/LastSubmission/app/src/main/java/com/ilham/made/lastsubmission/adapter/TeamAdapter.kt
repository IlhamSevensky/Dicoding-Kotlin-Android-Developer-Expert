package com.ilham.made.lastsubmission.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilham.made.lastsubmission.R
import com.ilham.made.lastsubmission.model.TeamModel
import com.ilham.made.lastsubmission.utils.Helper.setImageToView
import kotlinx.android.synthetic.main.item_row_team.view.*

class TeamAdapter(private var teams: MutableList<TeamModel>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    private var onTeamClickCallback: TeamClickCallback? = null

    fun setOnTeamClickCallback(onTeamClickCallback: TeamClickCallback) {
        this.onTeamClickCallback = onTeamClickCallback
    }

    interface TeamClickCallback {
        fun onItemClicked(data: TeamModel)
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: TeamModel) {
            with(itemView) {
                with(team) {
                    tv_team_id.text = idTeam.toString()
                    tv_team_name.text = strTeam ?: resources.getString(R.string.empty_data)

                    img_team.setImageResource(0)
                    if (strTeamBadge != null) {
                        Log.d("SSS", "JALAN")
                        Glide.with(context).clear(img_team)
                        setImageToView(context, strTeamBadge, img_team)
                    } else {
                        img_team.setImageResource(R.drawable.no_image_available)
                    }
                }

                setOnClickListener {
                    onTeamClickCallback?.onItemClicked(team)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder =
        TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row_team, parent, false
            )
        )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    fun setTeams(data: List<TeamModel>) {
        teams.clear()
        teams.addAll(data)
        notifyDataSetChanged()
    }
}