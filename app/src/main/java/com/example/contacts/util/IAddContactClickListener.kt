package com.example.contacts.util

import android.view.View

interface IAddContactClickListener {
    fun onSetLocationClicked(v: View)
    fun onSaveClicked(v: View)
    fun onCancelClicked(v: View)
}