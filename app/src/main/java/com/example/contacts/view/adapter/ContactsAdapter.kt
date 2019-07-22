package com.example.contacts.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.model.ContactDataBase
import com.example.contacts.model.Contacts
import com.example.contacts.view.fragment.ContactFragmentDirections
import com.example.contacts.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ContactsAdapter(private val viewModel: ContactViewModel, private val contactList: ArrayList<Contacts>) :
        RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){


    fun updateContactList(newContactList: List<Contacts>){
        contactList.clear()
        contactList.addAll(newContactList)
        notifyDataSetChanged()
    }

    private fun deleteContact(id: Int){
        viewModel.deleteContact(id)
        viewModel.refresh()
    }

    private fun updateContact(id: Int){
        viewModel.updateFavoriteContact(id)
        viewModel.refresh()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))


    override fun getItemCount(): Int = contactList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.name.text = contactList[position].firstName
        holder.itemView.lastName.text = contactList[position].lastName
        if (contactList[position].photo == null){
            holder.itemView.profileIcon.setImageResource(R.drawable.ic_account)
        } else{
            holder.itemView.profileIcon.setImageURI(contactList[position].photo!!.toUri())
        }

        holder.itemView.contactItem.setOnClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.contactMenuFavorite -> {
                        updateContact(contactList[position].uuid)
                        true
                    }
                    R.id.contactMenuDetail -> {
                        val action = ContactFragmentDirections.actionContactFragmentToContactDetailFragment()
                        action.contactUuid = contactList[position].uuid
                        Navigation.findNavController(holder.itemView).navigate(action)
                        true
                    }
                    R.id.contactMenuDelete -> {
                        deleteContact(contactList[position].uuid)
                       true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.contact_item_menu)  // inflate გავუკეთეთ menu-

            // popup-ზე რომ გამოჩნდეს icon-ი
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }

        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}
