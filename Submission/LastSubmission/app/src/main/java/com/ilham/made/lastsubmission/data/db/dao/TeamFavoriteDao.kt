package com.ilham.made.lastsubmission.data.db.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ilham.made.lastsubmission.data.db.entity.TeamFavoriteEntity

@Dao
interface TeamFavoriteDao {

    @Query("SELECT * FROM tb_team_favorite")
    fun getTeamFavoriteList(): LiveData<List<TeamFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeamFavorite(team: TeamFavoriteEntity)

    @Query("DELETE FROM tb_team_favorite WHERE idTeam = :idTeam")
    fun deleteTeamFavorite(idTeam: Int)

    @Query("SELECT COUNT(*) FROM tb_team_favorite WHERE idTeam = :idTeam")
    fun validateTeamFavorite(idTeam: Int): Int
}

@Database(entities = [TeamFavoriteEntity::class], version = 1, exportSchema = false)
abstract class TeamFavoriteDatabase : RoomDatabase() {
    abstract val favoriteTeamDao: TeamFavoriteDao
}

private lateinit var INSTANCE: TeamFavoriteDatabase

fun getTeamFavoriteDatabase(context: Context): TeamFavoriteDatabase {
    synchronized(TeamFavoriteDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TeamFavoriteDatabase::class.java,
                "team_favorite.db"
            ).build()
        }
    }
    return INSTANCE
}