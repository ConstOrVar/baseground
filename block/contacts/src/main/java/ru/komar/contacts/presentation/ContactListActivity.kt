package ru.komar.contacts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.komar.contacts.ContactScope
import ru.komar.contacts.rootComponent

internal class ContactListActivity : AppCompatActivity(), ContactScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent(this).diskFragmentProvider.diskFragment()
    }

}