package com.dariobrux.pokemon.app.other.extensions

import com.dariobrux.pokemon.app.data.models.ContactData
import com.github.tamir7.contacts.Contact

/**
 * Convert a [Contact] object to a [ContactData] object.
 */
fun Contact.toContactData(): ContactData {
    return ContactData().apply {
        this.displayName = this@toContactData.displayName
        this.phoneNumbers = this@toContactData.phoneNumbers.map {
            it.number.replace("\\s".toRegex(), "")
        }.distinct()
        this.picture = this@toContactData.photoUri
    }
}