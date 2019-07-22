package com.example.contacts.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Contacts::class), version = 1)
abstract class ContactDataBase:  RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile private var instance: ContactDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactDataBase::class.java,
            "contactdatabase"
        ).build()
    }

}
