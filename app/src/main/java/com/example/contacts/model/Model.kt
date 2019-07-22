package com.example.contacts.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contacts(
    @ColumnInfo(name ="first_name")
    var firstName: String,

    @ColumnInfo(name ="last_name")
    var lastName: String,

    @ColumnInfo(name ="phone_number")
    var phoneNumber: String,

    @ColumnInfo(name ="address_location")
    var location: String?,

    @ColumnInfo(name ="comment")
    var otherInfo: String?,

    @ColumnInfo(name ="favorite_contact")
    var favoriteContact: Int? = 0,

    @ColumnInfo(name ="contact_photo")
    var photo: String? = ""

){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
