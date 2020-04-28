package com.ilham.made.fourthsubmission.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ilham.made.fourthsubmission.data.db.entity.MatchFavoriteEntity
import com.ilham.made.fourthsubmission.model.MatchFavoriteModel
import com.ilham.made.fourthsubmission.model.MatchModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun setImageToView(context: Context, imagePath: String, imageView: ImageView){
        Glide.with(context).load(imagePath).into(imageView)
    }

    fun parseDateTimeFormat(date: String?, time: String?): String? = if (!date.isNullOrEmpty()) {
        if (time.isNullOrEmpty()) {
            val dateResult: Date? = dateFormatter("yyyy-MM-dd").parse(date)
            val dateFormat = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
            dateFormat.format(dateResult!!)
        } else {
            val dateResult: Date? = dateFormatter("yyyy-MM-dd hh:mm").parse("$date $time")
            val dateTimeFormat = SimpleDateFormat("dd MMMM yyyy hh:mm aa", Locale.getDefault())
            dateTimeFormat.timeZone = TimeZone.getTimeZone("GMT+7")
            dateTimeFormat.format(dateResult!!)
        }
    } else {
        "-"
    }

    private fun dateFormatter(format: String): DateFormat = SimpleDateFormat(format, Locale.getDefault())

    fun replaceStringForFootballData(string: String?): String? = string?.replace(";", "\n")

    fun replaceStringForPlayerData(string: String?): String? = string?.replace("; ", "\n")

    fun convertMatchFavoriteModelToMatchFavoriteEntity(data: MatchFavoriteModel): MatchFavoriteEntity {
        return MatchFavoriteEntity(
            id = data.id,
            idEvent = data.idEvent,
            strSport = data.strSport,
            idLeague = data.idLeague,
            strLeague = data.strLeague,
            strSeason = data.strSeason,
            strHomeTeam = data.strHomeTeam,
            strAwayTeam = data.strAwayTeam,
            intHomeScore = data.intHomeScore,
            intAwayScore = data.intAwayScore,
            intRound = data.intRound,
            strHomeGoalDetails = data.strHomeGoalDetails,
            strHomeRedCards = data.strHomeRedCards,
            strHomeYellowCards = data.strHomeYellowCards,
            strHomeLineupGoalkeeper = data.strHomeLineupGoalkeeper,
            strHomeLineupDefense = data.strHomeLineupDefense,
            strHomeLineupMidfield = data.strHomeLineupMidfield,
            strHomeLineupForward = data.strHomeLineupForward,
            strHomeFormation = data.strHomeFormation,
            strAwayGoalDetails = data.strAwayGoalDetails,
            strAwayRedCards = data.strAwayRedCards,
            strAwayYellowCards = data.strAwayYellowCards,
            strAwayLineupGoalkeeper = data.strAwayLineupGoalkeeper,
            strAwayLineupDefense = data.strAwayLineupDefense,
            strAwayLineupMidfield = data.strAwayLineupMidfield,
            strAwayLineupForward = data.strAwayLineupForward,
            strAwayFormation = data.strAwayFormation,
            intHomeShots = data.intHomeShots,
            intAwayShots = data.intAwayShots,
            dateEvent = data.dateEvent,
            strTime = data.strTime,
            strDate = data.strDate,
            idHomeTeam = data.idHomeTeam,
            idAwayTeam = data.idAwayTeam,
            strPostponed = data.strPostponed,
            strBadgeHomeTeam = data.strBadgeHomeTeam,
            strBadgeAwayTeam = data.strBadgeAwayTeam
        )
    }

    fun convertMatchModelToMatchFavoriteModel(data: MatchModel): MatchFavoriteModel {
        return MatchFavoriteModel(
            id = null,
            idEvent = data.idEvent,
            strSport = data.strSport,
            idLeague = data.idLeague,
            strLeague = data.strLeague,
            strSeason = data.strSeason,
            strHomeTeam = data.strHomeTeam,
            strAwayTeam = data.strAwayTeam,
            intHomeScore = data.intHomeScore,
            intAwayScore = data.intAwayScore,
            intRound = data.intRound,
            strHomeGoalDetails = data.strHomeGoalDetails,
            strHomeRedCards = data.strHomeRedCards,
            strHomeYellowCards = data.strHomeYellowCards,
            strHomeLineupGoalkeeper = data.strHomeLineupGoalkeeper,
            strHomeLineupDefense = data.strHomeLineupDefense,
            strHomeLineupMidfield = data.strHomeLineupMidfield,
            strHomeLineupForward = data.strHomeLineupForward,
            strHomeFormation = data.strHomeFormation,
            strAwayGoalDetails = data.strAwayGoalDetails,
            strAwayRedCards = data.strAwayRedCards,
            strAwayYellowCards = data.strAwayYellowCards,
            strAwayLineupGoalkeeper = data.strAwayLineupGoalkeeper,
            strAwayLineupDefense = data.strAwayLineupDefense,
            strAwayLineupMidfield = data.strAwayLineupMidfield,
            strAwayLineupForward = data.strAwayLineupForward,
            strAwayFormation = data.strAwayFormation,
            intHomeShots = data.intHomeShots,
            intAwayShots = data.intAwayShots,
            dateEvent = data.dateEvent,
            strTime = data.strTime,
            strDate = data.strDate,
            idHomeTeam = data.idHomeTeam,
            idAwayTeam = data.idAwayTeam,
            strPostponed = data.strPostponed,
            strBadgeHomeTeam = data.strBadgeHomeTeam,
            strBadgeAwayTeam = data.strBadgeAwayTeam
        )
    }

    fun convertFavoriteMatchModelToMatchModel(data: MatchFavoriteModel): MatchModel {
        return MatchModel(
            idEvent = data.idEvent,
            strSport = data.strSport,
            idLeague = data.idLeague,
            strLeague = data.strLeague,
            strSeason = data.strSeason,
            strHomeTeam = data.strHomeTeam,
            strAwayTeam = data.strAwayTeam,
            intHomeScore = data.intHomeScore,
            intAwayScore = data.intAwayScore,
            intRound = data.intRound,
            strHomeGoalDetails = data.strHomeGoalDetails,
            strHomeRedCards = data.strHomeRedCards,
            strHomeYellowCards = data.strHomeYellowCards,
            strHomeLineupGoalkeeper = data.strHomeLineupGoalkeeper,
            strHomeLineupDefense = data.strHomeLineupDefense,
            strHomeLineupMidfield = data.strHomeLineupMidfield,
            strHomeLineupForward = data.strHomeLineupForward,
            strHomeFormation = data.strHomeFormation,
            strAwayGoalDetails = data.strAwayGoalDetails,
            strAwayRedCards = data.strAwayRedCards,
            strAwayYellowCards = data.strAwayYellowCards,
            strAwayLineupGoalkeeper = data.strAwayLineupGoalkeeper,
            strAwayLineupDefense = data.strAwayLineupDefense,
            strAwayLineupMidfield = data.strAwayLineupMidfield,
            strAwayLineupForward = data.strAwayLineupForward,
            strAwayFormation = data.strAwayFormation,
            intHomeShots = data.intHomeShots,
            intAwayShots = data.intAwayShots,
            dateEvent = data.dateEvent,
            strTime = data.strTime,
            strDate = data.strDate,
            idHomeTeam = data.idHomeTeam,
            idAwayTeam = data.idAwayTeam,
            strPostponed = data.strPostponed,
            strBadgeHomeTeam = data.strBadgeHomeTeam,
            strBadgeAwayTeam = data.strBadgeAwayTeam
        )
    }

}