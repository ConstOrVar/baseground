package ru.komar.contacts_api

import android.content.Intent
import ru.komar.feature.Feature

interface ContactListActivityProvider : Feature {
    fun contactListActivityIntent(): Intent
}