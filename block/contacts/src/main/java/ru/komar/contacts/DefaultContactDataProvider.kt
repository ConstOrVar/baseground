package ru.komar.contacts

import ru.komar.contacts_api.ContactDataProvider
import ru.komar.contacts_api.model.Contact
import java.util.*

internal class DefaultContactDataProvider(
    private val contactsFeature: ContactsPlugin
) : ContactDataProvider {

    override fun loadContactByUuid(uuid: UUID): Contact? {
        contactsFeature.rootComponent
        return null
    }

}