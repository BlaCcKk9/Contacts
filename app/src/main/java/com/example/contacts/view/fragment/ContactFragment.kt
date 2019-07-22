package com.example.contacts.view.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.contacts.R
import com.example.contacts.model.Contacts
import com.example.contacts.view.adapter.ContactsAdapter
import com.example.contacts.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.fragment_contact.*


private lateinit var contactViewModel: ContactViewModel
private lateinit var contactsAdapter: ContactsAdapter
class ContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactsAdapter = ContactsAdapter(contactViewModel,arrayListOf())

        contactRV.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addContact.setOnClickListener {
            val action = ContactFragmentDirections.actionContactFragmentToAddContactFragment()
            Navigation.findNavController(it).navigate(action)
        }
        contactViewModel.refresh()
        observeContactViewModel()

    }

    fun observeContactViewModel(){
        contactViewModel.contact.observe(this, Observer {
            contactsAdapter.updateContactList(it)
        })
    }



}
