package com.ilham.made.fourthsubmission.data.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilham.made.fourthsubmission.model.MatchFavoriteModel

@Entity(tableName = "tb_match_favorite")
data class MatchFavoriteEntity constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val id: Int? = null,
    val idEvent: Int = 0,
    val strSport: String?,
    val idLeague: Int?,
    val strLeague: String?,
    val strSeason: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val intHomeScore: Int?,
    val intAwayScore: Int?,
    val intRound: Int?,
    val strHomeGoalDetails: String?,
    val strHomeRedCards: String?,
    val strHomeYellowCards: String?,
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupForward: String?,
    val strHomeFormation: String?,
    val strAwayGoalDetails: String?,
    val strAwayRedCards: String?,
    val strAwayYellowCards: String?,
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupForward: String?,
    val strAwayFormation: String?,
    val intHomeShots: Int?,
    val intAwayShots: Int?,
    val dateEvent: String?,
    val strTime: String?,
    val strDate: String?,
    val idHomeTeam: Int?,
    val idAwayTeam: Int?,
    val strPostponed: String?,
    var strBadgeHomeTeam: String?,
    var strBadgeAwayTeam: String?
)

fun List<MatchFavoriteEntity>.convertToMatchFavoriteModel(): List<MatchFavoriteModel> {
    return map {
        MatchFavoriteModel(
            id = it.id,
            idEvent = it.idEvent,
            strSport = it.strSport,
            idLeague = it.idLeague,
            strLeague = it.strLeague,
            strSeason = it.strSeason,
            strHomeTeam = it.strHomeTeam,
            strAwayTeam = it.strAwayTeam,
            intHomeScore = it.intHomeScore,
            intAwayScore = it.intAwayScore,
            intRound = it.intRound,
            strHomeGoalDetails = it.strHomeGoalDetails,
            strHomeRedCards = it.strHomeRedCards,
            strHomeYellowCards = it.strHomeYellowCards,
            strHomeLineupGoalkeeper = it.strHomeLineupGoalkeeper,
            strHomeLineupDefense = it.strHomeLineupDefense,
            strHomeLineupMidfield = it.strHomeLineupMidfield,
            strHomeLineupForward = it.strHomeLineupForward,
            strHomeFormation = it.strHomeFormation,
            strAwayGoalDetails = it.strAwayGoalDetails,
            strAwayRedCards = it.strAwayRedCards,
            strAwayYellowCards = it.strAwayYellowCards,
            strAwayLineupGoalkeeper = it.strAwayLineupGoalkeeper,
            strAwayLineupDefense = it.strAwayLineupDefense,
            strAwayLineupMidfield = it.strAwayLineupMidfield,
            strAwayLineupForward = it.strAwayLineupForward,
            strAwayFormation = it.strAwayFormation,
            intHomeShots = it.intHomeShots,
            intAwayShots = it.intAwayShots,
            dateEvent = it.dateEvent,
            strTime = it.strTime,
            strDate = it.strDate,
            idHomeTeam = it.idHomeTeam,
            idAwayTeam = it.idAwayTeam,
            strPostponed = it.strPostponed,
            strBadgeHomeTeam = it.strBadgeHomeTeam,
            strBadgeAwayTeam = it.strBadgeAwayTeam
        )
    }
}