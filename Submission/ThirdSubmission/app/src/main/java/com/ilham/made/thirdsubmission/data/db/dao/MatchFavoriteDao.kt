package com.ilham.made.thirdsubmission.data.db.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ilham.made.thirdsubmission.data.db.entity.MatchFavoriteEntity

@Dao
interface MatchFavoriteDao {

    @Query("SELECT * FROM tb_match_favorite")
    fun getMatchFavoriteList(): LiveData<List<MatchFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatchFavorite(match: MatchFavoriteEntity)

    @Query("DELETE FROM tb_match_favorite WHERE idEvent = :idEvent")
    fun deleteMatchFavorite(idEvent: Int)

    @Query("SELECT COUNT(*) FROM tb_match_favorite WHERE idEvent = :idEvent")
    fun validateMatchFavorite(idEvent: Int): Int
}

@Database(entities = [MatchFavoriteEntity::class], version = 1, exportSchema = false)
abstract class MatchFavoriteDatabase : RoomDatabase() {
    abstract val favoriteMatchDao: MatchFavoriteDao
}

private lateinit var INSTANCE: MatchFavoriteDatabase

fun getMatchFavoriteDatabase(context: Context): MatchFavoriteDatabase {
    synchronized(MatchFavoriteDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MatchFavoriteDatabase::class.java,
                "match_favorite.db"
            ).build()
        }
    }
    return INSTANCE
}