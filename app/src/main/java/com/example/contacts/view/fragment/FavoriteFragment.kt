package com.example.contacts.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.contacts.R
import com.example.contacts.view.adapter.FavoriteContactsAdapter
import com.example.contacts.viewmodel.FavoriteContactViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    private lateinit var favoriteContactViewModel: FavoriteContactViewModel
    private lateinit var favoriteContactsAdapter: FavoriteContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteContactViewModel = ViewModelProviders.of(this).get(FavoriteContactViewModel::class.java)
        favoriteContactsAdapter = FavoriteContactsAdapter(favoriteContactViewModel, arrayListOf())

        favoriteRV.apply {
            adapter = favoriteContactsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        favoriteContactViewModel.refresh()
        observeViewModel()

    }

    private fun observeViewModel(){
        favoriteContactViewModel.favoriteContact.observe(this, Observer {
            it?.let {
                favoriteContactsAdapter.updateContactList(it)
            }

        })
    }


}
