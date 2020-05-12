package ru.komar.contacts_api

import androidx.fragment.app.Fragment
import ru.komar.feature.Feature

interface ContactListFragmentProvider : Feature {
    fun contactListFragment(): Fragment
}