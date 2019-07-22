package com.example.contacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.contacts.model.ContactDataBase
import com.example.contacts.model.Contacts
import kotlinx.coroutines.launch

class FavoriteContactViewModel(appliaction: Application): BaseViewModel(appliaction) {

    var favoriteContact = MutableLiveData<List<Contacts>>()

    fun refresh(){
        getFavoriteContact()
    }

    private fun getFavoriteContact(){
        launch {
            val favorite = ContactDataBase(getApplication()).contactDao().getFavoriteContat(1)
            favoriteContactRetreived(favorite)
        }
    }

    private fun favoriteContactRetreived(favorite: List<Contacts>){
        favoriteContact.value = favorite
    }

    fun updateFavoriteContact(id: Int){
        launch {
            ContactDataBase(getApplication()).contactDao().updateFavoriteContact(0, id)
        }
    }

}