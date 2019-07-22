package com.example.contacts.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


interface IContactClickListener{
    fun onAddContactClicked(v: View)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, image: Int) {
    view.setImageResource(image)
}

