package com.ilham.made.lastsubmission.api

import com.ilham.made.lastsubmission.BuildConfig
import com.ilham.made.lastsubmission.model.*
import com.ilham.made.lastsubmission.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/all_leagues.php")
    fun getListLeague(): Call<ListResponse<LeagueModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/lookupleague.php")
    fun getLeagueDetail(
        @Query("id") leagueId: Int
    ): Call<ListResponse<LeagueModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/eventspastleague.php")
    fun getListPastMatchByLeague(
        @Query("id") leagueId: Int
    ): Call<ListResponseMatch<MatchModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/eventsnextleague.php")
    fun getListNextMatchByLeague(
        @Query("id") leagueId: Int
    ): Call<ListResponseMatch<MatchModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/lookupteam.php")
    fun getListTeamByTeamId(
        @Query("id") teamId: Int
    ): Call<ListResponseTeam<TeamBadge>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/searchevents.php")
    fun searchMatchByQuery(
        @Query("e") query: String
    ): Call<ListResponseSearchMatch<MatchModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/lookuptable.php")
    fun getFootballStandingsByLeague(
        @Query("l") leagueId: Int
    ): Call<ListResponseFootballStanding<StandingsModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/lookup_all_teams.php")
    fun getListTeamByLeague(
        @Query("id") leagueId: Int
    ): Call<ListResponseTeam<TeamModel>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/searchteams.php")
    fun searchTeamByQuery(
        @Query("t") query: String
    ): Call<ListResponseTeam<TeamModel>>
}