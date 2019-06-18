package com.example.nabha.grocerylist.model

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import com.example.nabha.grocerylist.database.BasketDao
import com.example.nabha.grocerylist.database.GroceryItemDao


/**
 * Created by nabha on 26/03/18.
 */
@Database(entities = [Basket::class, GroceryItem::class], version = 11)
abstract class AppDatabase : RoomDatabase() {

    abstract fun listDao(): BasketDao
    abstract fun itemListDao() : GroceryItemDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "grocery-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}