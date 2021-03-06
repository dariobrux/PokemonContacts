package com.dariobrux.pokemon.app.ui.main

import androidx.lifecycle.MutableLiveData
import com.dariobrux.pokemon.app.data.models.ContactData
import com.dariobrux.pokemon.app.data.models.DataInfo
import com.dariobrux.pokemon.app.data.models.Pokemon
import com.dariobrux.pokemon.app.other.Resource
import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest : TestCase() {

    private lateinit var openMocks: AutoCloseable
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MainRepository

    public override fun setUp() {
        super.setUp()
        openMocks = MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    override fun tearDown() {
        super.tearDown()
        openMocks.close()
    }

    @Test
    fun testNotExistingPokemonList() {
        Mockito.`when`(repository.getPokemon()).thenReturn(null)
        assertTrue(viewModel.getPokemonList() == null)
    }

    @Test
    fun testExistingPokemonList() {
        Mockito.`when`(repository.getPokemon()).thenReturn(MutableLiveData())
        assertTrue(viewModel.getPokemonList() != null)
    }

    @Test
    fun testEmptyContactList() {
        Mockito.`when`(repository.getContactList()).thenReturn(listOf())
        assertTrue(viewModel.getContactList().isEmpty())
    }

    @Test
    fun testNotEmptyContactList() {
        Mockito.`when`(repository.getContactList()).thenReturn(listOf(ContactData(), ContactData()))
        assertTrue(viewModel.getContactList().isNotEmpty())
    }

    @Test
    fun testContactListWith3Contacts() {
        Mockito.`when`(repository.getContactList()).thenReturn(listOf(ContactData(), ContactData(), ContactData()))
        assertEquals(viewModel.getContactList().size, 3)
    }

    @Test
    fun testGetNotExistingData() {
        Mockito.`when`(repository.getPokemon()).thenReturn(null)
        assertTrue(viewModel.getPokemonList() == null)
    }

    @Test
    fun testRefreshPokemon() {
        val dataInfo = DataInfo().apply {
            this.pokemonList = List(20) { Pokemon() }
        }
        val resource = Resource(Resource.Status.SUCCESS, dataInfo, null)
        val liveData = MutableLiveData(resource)
        Mockito.`when`(repository.getPokemon()).thenReturn(liveData)
        val result = viewModel.refreshPokemon()
        assertTrue(result?.value?.data?.pokemonList?.size == 20)
    }
}