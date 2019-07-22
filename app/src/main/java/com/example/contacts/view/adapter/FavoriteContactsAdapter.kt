package com.example.contacts.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.model.Contacts
import com.example.contacts.view.fragment.ContactFragmentDirections
import com.example.contacts.view.fragment.FavoriteFragmentDirections
import com.example.contacts.viewmodel.FavoriteContactViewModel
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.contact_item_favorite.view.*
import java.lang.Exception

class FavoriteContactsAdapter(private val viewModel: FavoriteContactViewModel, private val favoriteList: ArrayList<Contacts>) :
        RecyclerView.Adapter<FavoriteContactsAdapter.ViewHolder>(){

    fun updateContactList(newContactList: List<Contacts>){
        favoriteList.clear()
        favoriteList.addAll(newContactList)
        notifyDataSetChanged()
    }

    private fun deleteContact(id: Int){
        viewModel.updateFavoriteContact(id)
        viewModel.refresh()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_favorite, parent, false))


    override fun getItemCount(): Int = favoriteList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.favoriteName.text = favoriteList[position].firstName
        holder.itemView.favoriteLastName.text = favoriteList[position].lastName
        if(favoriteList[position].photo == null){
            holder.itemView.favoriteIcon.setImageResource(R.drawable.ic_account)
        } else{
            holder.itemView.favoriteIcon.setImageURI(favoriteList[position].photo!!.toUri())
        }


        holder.itemView.setOnClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.contactMenuFavorite -> {
                        Toast.makeText(holder.itemView.context, "Aldready Favorite", Toast.LENGTH_LONG).show()
                        true
                    }
                    R.id.contactMenuDetail -> {
                        val action = FavoriteFragmentDirections.actionFavoriteFragmentToContactDetailFragment()
                        action.contactUuid = favoriteList[position].uuid
                        Navigation.findNavController(holder.itemView).navigate(action)
                        true
                    }
                    R.id.contactMenuDelete -> {
                        deleteContact(this.favoriteList[position].uuid)
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
