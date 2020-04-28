package com.ilham.made.lastsubmission.data.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilham.made.lastsubmission.model.TeamFavoriteModel

@Entity(tableName = "tb_team_favorite")
data class TeamFavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val id: Int? = null,
    val idTeam: Int = 0,
    val strTeam: String? = null,
    val strStadium: String? = null,
    val strDescriptionEN: String? = null,
    val strTeamBadge: String? = null,
    val strSport: String? = null
)

fun List<TeamFavoriteEntity>.convertToTeamFavoriteModel() : List<TeamFavoriteModel> {
    return map {
        TeamFavoriteModel(
            id = it.id,
            idTeam = it.idTeam,
            strTeam = it.strTeam,
            strStadium = it.strStadium,
            strDescriptionEN = it.strDescriptionEN,
            strTeamBadge = it.strTeamBadge,
            strSport = it.strSport
        )
    }
}