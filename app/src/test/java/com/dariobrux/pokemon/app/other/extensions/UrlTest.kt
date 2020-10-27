package com.dariobrux.pokemon.app.other.extensions

import junit.framework.TestCase
import org.junit.Test

class UrlTest : TestCase() {

    @Test
    fun testGetIdFromUrl1() {
        val url = "https://www.testableapi.com/4"
        assertEquals(url.getIdFromUrl(), -1)
    }

    @Test
    fun testGetIdFromUrl2() {
        val url = "https://www.testableapi.com/4/"
        assertEquals(url.getIdFromUrl(), 4)
    }

    @Test
    fun testGetIdFromUrl3() {
        val url = "https://www.testableapi.com"
        assertEquals(url.getIdFromUrl(), -1)
    }

}