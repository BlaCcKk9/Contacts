package com.example.contacts.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.model.ContactDao
import com.example.contacts.model.ContactDataBase
import com.example.contacts.model.Contacts
import com.example.contacts.util.*
import kotlinx.coroutines.launch

class AddContactViewModel(application: Application): BaseViewModel(application) {

    var contact = MutableLiveData<Contacts>()

    fun setNewContact(contact: Contacts){
        this.contact.value = contact
        saveNewContact()
    }

    private fun saveNewContact(){

        launch {
          ContactDataBase(getApplication()).contactDao().insertAll(contact.value!!)
        }

    }

}