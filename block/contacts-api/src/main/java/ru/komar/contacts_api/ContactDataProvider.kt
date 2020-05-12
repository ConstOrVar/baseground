package ru.komar.contacts_api

import ru.komar.contacts_api.model.Contact
import ru.komar.feature.Feature
import java.util.*

interface ContactDataProvider : Feature {
    fun loadContactByUuid(uuid: UUID): Contact?
}