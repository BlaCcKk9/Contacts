package com.example.contacts.viewmodel



import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.contacts.model.ContactDataBase
import com.example.contacts.model.Contacts

import kotlinx.coroutines.launch


class ContactViewModel(application: Application): BaseViewModel(application) {

    val contact = MutableLiveData<List<Contacts>>()

    fun refresh(){
        getContactsFromDataBase()
    }

    fun deleteContact(id: Int){
        launch {
            ContactDataBase(getApplication()).contactDao().deleteContact(id)
        }
    }

    fun updateFavoriteContact(id: Int){
        launch {
            ContactDataBase(getApplication()).contactDao().updateFavoriteContact(1, id)
        }
    }

    private fun getContactsFromDataBase(){
        launch {
            val data = ContactDataBase(getApplication()).contactDao().getAllContact()
            contactRetreived(data)
        }
    }

    private fun contactRetreived(contactList: List<Contacts>){
        contact.value = contactList
    }

}