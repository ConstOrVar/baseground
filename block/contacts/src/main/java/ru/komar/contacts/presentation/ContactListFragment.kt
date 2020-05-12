package ru.komar.contacts.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.komar.contacts.ContactScope
import ru.komar.contacts.rootComponent

internal class ContactListFragment : Fragment(), ContactScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootComponent(requireContext()).diskFragmentProvider.diskFragment()
    }

}