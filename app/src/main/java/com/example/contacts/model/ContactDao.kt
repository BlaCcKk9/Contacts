package com.example.contacts.model

import android.content.ClipData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    //      INSERT

    @Insert
    suspend fun insertAll(vararg contacts: Contacts)

    //      SELECT
    @Query("SELECT * FROM contacts")
    suspend fun getAllContact(): List<Contacts>

    @Query("SELECT * FROM contacts WHERE uuid = :id")
    suspend fun getContact(id: Int): Contacts

    @Query("SELECT * FROM contacts WHERE favorite_contact = :isFavorite")
    suspend fun getFavoriteContat(isFavorite: Int): List<Contacts>

    //     DELETE

    @Query("DELETE FROM contacts")
    suspend fun deleteAllcontacts()

    @Query("DELETE FROM contacts WHERE uuid = :id")
    suspend fun deleteContact(id: Int)

    //     UPDATE

    @Query("UPDATE contacts SET favorite_contact = :isFavorite WHERE uuid = :id")
    suspend fun updateFavoriteContact(isFavorite: Int, id: Int)


}