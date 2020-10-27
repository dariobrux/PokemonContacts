package com.dariobrux.pokemon.app.ui.main

import androidx.lifecycle.MutableLiveData
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
    fun testGetExistingData() {
        Mockito.`when`(repository.getPokemon()).thenReturn(MutableLiveData())
        assertTrue(viewModel.getPokemon() != null)
    }

    @Test
    fun testGetNotExistingData() {
        Mockito.`when`(repository.getPokemon()).thenReturn(null)
        assertTrue(viewModel.getPokemon() == null)
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