package com.ilham.made.secondsubmission.webservices

import com.ilham.made.secondsubmission.BuildConfig
import com.ilham.made.secondsubmission.models.LeagueModel
import com.ilham.made.secondsubmission.models.MatchModel
import com.ilham.made.secondsubmission.models.TeamBadge
import com.ilham.made.secondsubmission.responses.ListResponse
import com.ilham.made.secondsubmission.responses.ListResponseMatch
import com.ilham.made.secondsubmission.responses.ListResponseSearchMatch
import com.ilham.made.secondsubmission.responses.ListResponseTeam
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
    fun getListTeam(
        @Query("id") teamId: Int
    ): Call<ListResponseTeam<TeamBadge>>

    @GET(BuildConfig.BASE_URL_TSDB + BuildConfig.TSDB_API_KEY + "/searchevents.php")
    fun searchMatchByQuery(
        @Query("e") query: String
    ): Call<ListResponseSearchMatch<MatchModel>>

}