package com.dariobrux.pokemon.app.data.models

import java.io.Serializable

class ContactData : Serializable {

    var displayName: String? = ""
    var picture: String? = ""
    var phoneNumbers: List<String>? = listOf()
}