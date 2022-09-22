package com.android.tubes_pbp.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class,Experience::class],
    version = 1
)
abstract class TubesDB: RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        @Volatile private var instance : TubesDB? = null
        private val LOCK = Any()
        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TubesDB::class.java,
            "tubes.db"
        ).build()
    }
}