package ru.komar.contacts

import androidx.fragment.app.Fragment
import ru.komar.contacts.presentation.ContactListFragment
import ru.komar.contacts_api.ContactListFragmentProvider

internal class DefaultContactListFragmentProvider : ContactListFragmentProvider {

    override fun contactListFragment(): Fragment {
        return ContactListFragment()
    }

}