package com.ilham.made.fourthsubmission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchModel(
    @SerializedName("idEvent")
    val idEvent: Int = 0,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("idLeague")
    val idLeague: Int? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strSeason")
    val strSeason: String? = null,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String? = null,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String? = null,
    @SerializedName("intHomeScore")
    val intHomeScore: Int? = null,
    @SerializedName("intAwayScore")
    val intAwayScore: Int? = null,
    @SerializedName("intRound")
    val intRound: Int? = null,
    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String? = null,
    @SerializedName("strHomeRedCards")
    val strHomeRedCards: String? = null,
    @SerializedName("strHomeYellowCards")
    val strHomeYellowCards: String? = null,
    @SerializedName("strHomeLineupGoalkeeper")
    val strHomeLineupGoalkeeper: String? = null,
    @SerializedName("strHomeLineupDefense")
    val strHomeLineupDefense: String? = null,
    @SerializedName("strHomeLineupMidfield")
    val strHomeLineupMidfield: String? = null,
    @SerializedName("strHomeLineupForward")
    val strHomeLineupForward: String? = null,
    @SerializedName("strHomeFormation")
    val strHomeFormation: String? = null,
    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String? = null,
    @SerializedName("strAwayRedCards")
    val strAwayRedCards: String? = null,
    @SerializedName("strAwayYellowCards")
    val strAwayYellowCards: String? = null,
    @SerializedName("strAwayLineupGoalkeeper")
    val strAwayLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupDefense")
    val strAwayLineupDefense: String? = null,
    @SerializedName("strAwayLineupMidfield")
    val strAwayLineupMidfield: String? = null,
    @SerializedName("strAwayLineupForward")
    val strAwayLineupForward: String? = null,
    @SerializedName("strAwayFormation")
    val strAwayFormation: String? = null,
    @SerializedName("intHomeShots")
    val intHomeShots: Int? = null,
    @SerializedName("intAwayShots")
    val intAwayShots: Int? = null,
    @SerializedName("dateEvent")
    val dateEvent: String? = null,
    @SerializedName("strTime")
    val strTime: String? = null,
    @SerializedName("strDate")
    val strDate: String? = null,
    @SerializedName("idHomeTeam")
    val idHomeTeam: Int? = null,
    @SerializedName("idAwayTeam")
    val idAwayTeam: Int? = null,
    @SerializedName("strPostponed")
    val strPostponed: String? = null,
    var strBadgeHomeTeam: String? = null,
    var strBadgeAwayTeam: String? = null
) : Parcelable

data class TeamBadge(
    @SerializedName("idTeam")
    val idTeam: Int? = null,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null
)