package com.example.contacts.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.contacts.model.ContactDataBase
import com.example.contacts.model.Contacts
import kotlinx.coroutines.launch

class ContactDetailViewModel(application: Application): BaseViewModel(application){

    val detailContact = MutableLiveData<Contacts>()

    fun refresh(id: Int){
        getDetailContact(id)
    }

    private fun getDetailContact(id: Int){
        launch {
            val contact =  ContactDataBase(getApplication()).contactDao().getContact(id)
            contactRetreived(contact)
        }
    }

    private fun contactRetreived(contact: Contacts){
        detailContact.value = contact
    }


}

