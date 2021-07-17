package com.example.cakeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cakeslist.data.CakeRepository
import com.example.cakeslist.data.CakesApi
import com.example.cakeslist.data.models.Cake
import com.example.cakeslist.domain.usecases.GetCakesUseCase
import com.example.cakeslist.presentation.home.HomeViewModel
import com.example.cakeslist.presentation.models.LoadingState
import com.example.cakeslist.utils.DispatcherFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class LoadHomeListTest : KoinTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    lateinit var homeViewModel: HomeViewModel

    lateinit var useCase: GetCakesUseCase

    lateinit var repository: CakeRepository

    private val mockList = listOf(
        Cake("g", "1", ""),
        Cake("b", "1", ""),
        Cake("a", "1", ""),
        Cake("g", "1", ""),
        Cake("a", "1", "")
    )

    private var cakesApi: CakesApi = mockk {
        coEvery { getCakes() } returns runBlocking { mockList }
    }

    @MockK
    lateinit var dispatcherFactory: DispatcherFactory


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = CakeRepository(cakesApi)
        useCase = GetCakesUseCase(repository)
        homeViewModel = HomeViewModel(dispatcherFactory, useCase)
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check if the list have items`() {
        runBlocking {
            homeViewModel.cakesList.value?.let { assert(!it.isNullOrEmpty()) }
        }
    }

    @Test
    fun `check repository data`() {
        runBlocking {
            repository.getCakes().collect {
                assert(!it.isNullOrEmpty())
            }
        }
    }

    @Test
    fun `check distinct and order data`() {
        runBlocking {
            repository.getCakes().collect {
                val wishData = listOf(Cake("a", "1", ""), Cake("b", "1", ""), Cake("g", "1", ""))
                assert(
                    it.zip(wishData)
                        .all { pair -> pair.first.title == pair.second.title } && it.size == wishData.size
                )
            }
        }
    }

    @Test
    fun `check data failed`() {
        homeViewModel = mockk { every { loadingState.value } returns LoadingState.error("") }
        val state = homeViewModel.loadingState.value
        assert(state == LoadingState.error(""))
    }
}