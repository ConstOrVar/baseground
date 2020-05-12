package ru.komar.contacts.model

import ru.komar.contacts_api.model.Contact
import java.util.*

data class DefaultContact(
    override val uuid: UUID,
    override val name: String,
    override val dayOfBirth: Date?,
    val phone: String?,
    val email: String?
) : Contact