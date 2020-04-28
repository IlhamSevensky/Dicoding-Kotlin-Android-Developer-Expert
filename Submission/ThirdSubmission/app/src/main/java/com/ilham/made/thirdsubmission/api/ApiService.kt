package com.ilham.made.thirdsubmission.api

import com.ilham.made.thirdsubmission.BuildConfig
import com.ilham.made.thirdsubmission.model.LeagueModel
import com.ilham.made.thirdsubmission.model.MatchModel
import com.ilham.made.thirdsubmission.model.TeamBadge
import com.ilham.made.thirdsubmission.response.ListResponse
import com.ilham.made.thirdsubmission.response.ListResponseMatch
import com.ilham.made.thirdsubmission.response.ListResponseSearchMatch
import com.ilham.made.thirdsubmission.response.ListResponseTeam
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
}