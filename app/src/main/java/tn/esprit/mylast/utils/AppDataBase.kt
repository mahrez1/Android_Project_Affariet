package tn.esprit.mylast.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import  tn.esprit.mylast.dao.UserDAO
import  tn.esprit.mylast.data.User


//TODO 8 "Create the AppDataBase class"
@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun UserDAO(): UserDAO

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "user")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return instance!!
        }
    } }
