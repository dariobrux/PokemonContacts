package com.dariobrux.pokemon.app.other.extensions

import com.github.tamir7.contacts.Contact
import com.github.tamir7.contacts.PhoneNumber
import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mockito

class ContactTest : TestCase() {

    @Test
    fun testToContactDisplayName() {
        val contact = Mockito.mock(Contact::class.java)
        Mockito.`when`(contact.displayName).thenReturn("Alfred")
        val contactData = contact.toContactData()
        assertTrue(contactData.displayName == "Alfred")
    }

    @Test
    fun testToContactPhoneNumber() {
        val contact = Mockito.mock(Contact::class.java)
        val phoneNumber = Mockito.mock(PhoneNumber::class.java)
        Mockito.`when`(phoneNumber.number).thenReturn("123456789")
        Mockito.`when`(contact.phoneNumbers).thenReturn(listOf(phoneNumber))
        val contactData = contact.toContactData()
        assertTrue(contactData.phoneNumbers?.size == 1)
        assertTrue(contactData.phoneNumbers?.firstOrNull() == "123456789")
    }

    @Test
    fun testToContactPicture() {
        val contact = Mockito.mock(Contact::class.java)
        Mockito.`when`(contact.photoUri).thenReturn("imagePath")
        val contactData = contact.toContactData()
        assertTrue(contactData.picture == "imagePath")
    }
}