package com.example.contacts.view.fragment


import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.contacts.R
import com.example.contacts.databinding.FragmentContactDetailBinding
import com.example.contacts.model.Contacts
import com.example.contacts.viewmodel.ContactDetailViewModel
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import java.io.ByteArrayOutputStream


@Suppress("CAST_NEVER_SUCCEEDS")
class ContactDetailFragment : Fragment() {

    private var contactId: Int = 0
    private lateinit var contactDetailViewModel: ContactDetailViewModel
    private lateinit var dataBinding: FragmentContactDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactDetailViewModel = ViewModelProviders.of(this).get(ContactDetailViewModel::class.java)

        arguments?.let {
            contactId = ContactDetailFragmentArgs.fromBundle(it).contactUuid
        }
        contactDetailViewModel.refresh(contactId)
        observeViewModel()

    }

    private fun observeViewModel(){
        contactDetailViewModel.detailContact.observe(this, Observer {
            it?.let {
                updateUI(it)
            }
        })
    }

    private fun updateUI(contact: Contacts){
        dataBinding.contact = contact
        dataBinding.space = "  "

        if(contact.photo == null){
            detailImage.setImageResource(R.drawable.ic_account)
        } else{
            detailImage.setImageURI(contact.photo!!.toUri())
        }
    }


}
