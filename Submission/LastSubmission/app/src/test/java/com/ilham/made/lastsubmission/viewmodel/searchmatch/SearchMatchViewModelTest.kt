package com.ilham.made.lastsubmission.viewmodel.searchmatch

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ilham.made.lastsubmission.model.MatchModel
import com.ilham.made.lastsubmission.ui.search.match.SearchMatchViewModel
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
class SearchMatchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationMock: Application
    private lateinit var spySearchMatchViewModel: SearchMatchViewModel

    @Mock
    lateinit var observer: Observer<List<MatchModel>>

    @Before
    fun setUp() {
        initMocks(this)
        applicationMock = mock(Application::class.java)
        val searchMatchViewModel =
            SearchMatchViewModel(
                applicationMock
            )
        spySearchMatchViewModel = spy(searchMatchViewModel)
    }

    @Test
    fun searchViewModelObserverTest() {
        spySearchMatchViewModel.getListSearchMatch()?.observeForever(observer)
        spySearchMatchViewModel.searchMatch(anyString())

        verify(observer).onChanged(anyList())
        Assert.assertEquals(null, spySearchMatchViewModel.getListSearchMatch()?.value)
    }

    @Test
    fun searchViewModelMethodTest() {
        spySearchMatchViewModel.getListSearchMatch()
        spySearchMatchViewModel.searchMatch(anyString())
        spySearchMatchViewModel.searchMatch(anyString())

        verify(spySearchMatchViewModel, times(1)).getListSearchMatch()
        verify(spySearchMatchViewModel, times(2)).searchMatch(anyString())
    }

}