package com.ilham.made.footballclub.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ilham.made.footballclub.model.Favorite.Companion.ID
import com.ilham.made.footballclub.model.Favorite.Companion.TABLE_FAVORITE
import com.ilham.made.footballclub.model.Favorite.Companion.TEAM_BADGE
import com.ilham.made.footballclub.model.Favorite.Companion.TEAM_ID
import com.ilham.made.footballclub.model.Favorite.Companion.TEAM_NAME
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(TABLE_FAVORITE, true,
            ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TEAM_ID to TEXT + UNIQUE,
            TEAM_NAME to TEXT,
            TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)