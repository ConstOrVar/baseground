package ru.komar.contacts_api.model

import java.util.*

interface Contact {
    val uuid: UUID
    val name: String
    val dayOfBirth: Date?
}