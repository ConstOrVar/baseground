package ru.komar.contacts

import android.content.Context
import android.content.Intent
import ru.komar.contacts.presentation.ContactListActivity
import ru.komar.contacts_api.ContactListActivityProvider

internal class DefaultContactListActivityProvider(
    private val context: Context
) : ContactListActivityProvider {

    override fun contactListActivityIntent(): Intent {
        return Intent(context, ContactListActivity::class.java)
    }

}