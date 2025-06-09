package com.jurajkuliska.eightqueens.presentation.screens.gameplay

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardStateTestData
import com.jurajkuliska.eightqueens.game.domain.usecase.GetBoardStateHandlerUseCase
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.game.presentation.screens.gameplay.GamePlayViewModel
import com.jurajkuliska.eightqueens.presentation.testutils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class GamePlayViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getBoardStateHandlerUseCaseMock = mockk<GetBoardStateHandlerUseCase>()
    private val boardFlow = MutableStateFlow(BoardStateTestData.boardStateDefault)
    private val boardStateHandlerMock = mockk<BoardStateHandler> {
        every { board } returns boardFlow
    }

    @Test
    fun test_uiState_when_boardStateHandler_emits_different_values() = runTest {

    }

    @Test
    fun test_onTileTap_placeQueen_returns_Conflict() = runTest {

    }

    @Test
    fun test_onTileTap_placeQueen_returns_Success_AddedAndWin() = runTest {

    }

    @Test
    fun test_onTileTap_placeQueen_returns_Success_Removed() = runTest {

    }

    @Test
    fun test_onTileTap_placeQueen_returns_Success_Added() = runTest {

    }

    @Test
    fun test_onResetClick_without_previous_win() = runTest {

    }

    @Test
    fun test_onResetClick_with_previous_win() = runTest {

    }

    @Test
    fun test_onPickDifferentBoardClick_emits_navigateBack_uiEvent() = runTest {

    }

    @Test
    fun test_onBackClick_emits_navigateBack_uiEvent() = runTest {
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut()
        sut.uiEvent.test {
            sut.onBackClick()
            assertThat(awaitItem()).isEqualTo(GamePlayViewModel.UiEvent.NavigateBack)
        }
    }

    private fun initSut(navArgs: GameRoute.GamePlay = Companion.navArgs) = GamePlayViewModel(
        navArgs = navArgs,
        getBoardStateHandlerUseCase = getBoardStateHandlerUseCaseMock,
    )

    private companion object {
        val navArgs = GameRoute.GamePlay(boardSize = 4, allSolutionsCount = 89)
    }
}