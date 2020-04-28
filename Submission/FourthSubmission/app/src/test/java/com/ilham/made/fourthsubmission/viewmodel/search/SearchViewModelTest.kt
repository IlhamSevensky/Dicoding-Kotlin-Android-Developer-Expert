package com.ilham.made.fourthsubmission.viewmodel.search

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.fourthsubmission.model.MatchModel
import com.ilham.made.fourthsubmission.ui.search.SearchViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var applicationMock: Application
    lateinit var spySearchViewModel: SearchViewModel

    @Mock
    lateinit var observer: Observer<List<MatchModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val searchViewModel = SearchViewModel(applicationMock)
        spySearchViewModel = spy(searchViewModel)
    }

    @Test
    fun searchViewModelObserverTest() {
        spySearchViewModel.getListSearchMatch()?.observeForever(observer)
        spySearchViewModel.searchMatch(anyString())

        verify(observer).onChanged(anyList())
        Assert.assertEquals(null, spySearchViewModel.getListSearchMatch()?.value)
    }

    @Test
    fun searchViewModelMethodTest() {
        spySearchViewModel.getListSearchMatch()
        spySearchViewModel.searchMatch(anyString())
        spySearchViewModel.searchMatch(anyString())

        verify(spySearchViewModel, times(1)).getListSearchMatch()
        verify(spySearchViewModel, times(2)).searchMatch(anyString())
    }

}