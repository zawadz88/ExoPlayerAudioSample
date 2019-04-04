package com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel

import com.github.zawadz88.exoplayeraudiosample.domain.interactor.LoadPlaylist
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isNull
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MainViewModelTest {

    @Mock
    lateinit var mockLoadPlaylist: LoadPlaylist

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Test
    fun `loadContent() should execute LoadPlaylist use case`() {
        // when
        mainViewModel.loadContent()

        // then
        verify(mockLoadPlaylist).invoke(any(), isNull())
    }
}
