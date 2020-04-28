package com.ilham.made.lastsubmission.api

import org.junit.Assert
import org.junit.Test

class ApiRequestTest {

    @Test
    fun getListLeagueTest() {
        val response = ApiClient.instance.getListLeague().execute()
        print("League List Result : \n ${response.body()?.result}")
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun getLeagueDetailByIdTest() {
        val leagueId = 4328
        val response = ApiClient.instance.getLeagueDetail(leagueId).execute()
        print("League Detail Result : \n ${response.body()?.result}")
        Assert.assertEquals("English Premier League", response.body()?.result?.get(0)?.strLeague)
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun getListPastMatchByLeagueTest() {
        val leagueId = 4328
        val response = ApiClient.instance.getListPastMatchByLeague(leagueId).execute()
        print("Last Match Result : \n ${response.body()?.result}")
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun getListNextMatchByLeagueTest() {
        val leagueId = 4328
        val response = ApiClient.instance.getListNextMatchByLeague(leagueId).execute()
        print("Next Match Result : \n ${response.body()?.result}")
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun getTeamByTeamIdTest() {
        val teamId = 133613
        val response = ApiClient.instance.getListTeamByTeamId(teamId).execute()
        print("Team Badge Result : \n ${response.body()?.result}")
        Assert.assertEquals(teamId, response.body()?.result?.get(0)?.idTeam)
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun searchMatchByQueryTest() {
        val query = "Real Madrid vs Barcelona"
        val response = ApiClient.instance.searchMatchByQuery(query).execute()
        print("Search Result : \n ${response.body()?.result}")
        Assert.assertEquals(200,response.code())
    }

    @Test
    fun getFootballStandingsByLeagueTest() {
        val leagueId = 4328
        val response = ApiClient.instance.getFootballStandingsByLeague(leagueId).execute()
        print("Standing Result : \n${response.body()?.result}")
        Assert.assertEquals(200, response.code())
    }

    @Test
    fun getListTeamByLeagueTest() {
        val leagueId = 4328
        val response = ApiClient.instance.getListTeamByLeague(leagueId).execute()
        print("Team Result : \n ${response.body()?.result}")
        Assert.assertEquals(200, response.code())
    }

    @Test
    fun searchTeamByQueryTest() {
        val query = "Arsenal"
        val response = ApiClient.instance.searchTeamByQuery(query).execute()
        print("Team Result : \n ${response.body()?.result}")
        Assert.assertEquals(200, response.code())
    }
}