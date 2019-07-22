package com.example.contacts.view.fragment


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.contacts.R
import com.example.contacts.databinding.FragmentAddContactBinding
import com.example.contacts.model.Contacts
import com.example.contacts.util.IAddContactClickListener
import com.example.contacts.viewmodel.AddContactViewModel
import kotlinx.android.synthetic.main.fragment_add_contact.*
import java.io.ByteArrayOutputStream
import java.io.IOException


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddContactFragment : Fragment(), IAddContactClickListener {

    private lateinit var addContactViewModel: AddContactViewModel
    private lateinit var dataBinding: FragmentAddContactBinding
    private val GALLERY = 1
    private lateinit var streetLocation: String
    private var photo: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.listener = this
         // viewmodel-ის ინიციალიზაცია
        addContactViewModel = ViewModelProviders.of(this).get(AddContactViewModel::class.java)

        arguments.let {
            streetLocation = AddContactFragmentArgs.fromBundle(it!!).addressName   // მისამართის დაბრუნება Map Fragment-იდან
        }
        streetLocationEdt.text = streetLocation

        addPhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY)

        }

    }

    // ამუშავდება როცა user-ი დააჭერს ლოკაციის არჩევის ღილაკს
    override fun onSetLocationClicked(v: View) {
        val action = AddContactFragmentDirections.actionAddContactFragmentToMapFragment()
        Navigation.findNavController(v).navigate(action)
    }

    // ამუშავდება როცა user-ი დააჭერს შენახვის ღილაკს
    override fun onSaveClicked(v: View) {
        if(!TextUtils.isEmpty(nameEdt.text.toString()) || !TextUtils.isEmpty(lastNameEdt.text.toString()) || !TextUtils.isEmpty(phoneNumberEdt.text.toString())){
            val contact = Contacts(nameEdt.text.toString(), lastNameEdt.text.toString(), phoneNumberEdt.text.toString(),
                streetLocationEdt.text.toString(), addComentar.text.toString(),0, photo)
            addContactViewModel.setNewContact(contact)
            val action = AddContactFragmentDirections.actionAddContactFragmentToContactFragment()
            Navigation.findNavController(v).navigate(action)
        } else{
            Toast.makeText(context, "Please ender text", Toast.LENGTH_LONG).show()
        }

    }

    //ამუშავდება როცა user-ი დააჭერს გაუქმების ღილაკს
    override fun onCancelClicked(v: View) {
        val action = AddContactFragmentDirections.actionAddContactFragmentToContactFragment()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY){
            if (data != null)
            {
                val contentURI = data.data
                photo = contentURI.toString()
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, ByteArrayOutputStream())
                    setPhoto(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setPhoto(photo: Bitmap){
        addPhoto.background = null
        addPhoto.setImageBitmap(photo)
    }



}
