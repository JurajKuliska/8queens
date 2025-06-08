package com.jurajkuliska.eightqueens.presentation.screens.initial

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialViewModel
import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialViewModel.UiState
import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialViewModel.UiState.BoardSizePickerState
import com.jurajkuliska.eightqueens.presentation.testutils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class InitialViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test onBoardSizePickerOpen and then onBoardSizePicked`() = runTest {
        val sut = initSut()
        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(defaultUiState)

            sut.onBoardSizePickerOpen()
            assertThat(awaitItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = true, selectedOption = BoardSize.EightByEight)))

            sut.onBoardSizePicked(boardSize = BoardSize.SixBySix)
            assertThat(expectMostRecentItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.SixBySix)))

            sut.onBoardSizePicked(boardSize = BoardSize.FourByFour)
            assertThat(awaitItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.FourByFour)))
        }
    }

    @Test
    fun `test onBoardSizePickerOpen and then onBoardSizePickerDismissed`() = runTest {
        val sut = initSut()
        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(defaultUiState)

            sut.onBoardSizePickerOpen()
            assertThat(awaitItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = true, selectedOption = BoardSize.EightByEight)))

            sut.onBoardSizePicked(boardSize = BoardSize.SixBySix)
            assertThat(expectMostRecentItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.SixBySix)))

            sut.onBoardSizePickerOpen()
            assertThat(awaitItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = true, selectedOption = BoardSize.SixBySix)))

            sut.onBoardSizePickerDismiss()
            assertThat(awaitItem())
                .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.SixBySix)))
        }
    }

    @Test
    fun `test onNext - without changing selection - verify correct UiEvent emitted`() = runTest {
        mockkObject(BoardSize.EightByEight)
        every { BoardSize.EightByEight.size } returns 44
        every { BoardSize.EightByEight.allSolutionsCount } returns 21
        val sut = initSut()
        sut.uiEvent.test {
            sut.onNext()
            assertThat(awaitItem()).isEqualTo(InitialViewModel.UiEvent.NavigateToGamePlay(route = GameRoute.GamePlay(boardSize = 44, allSolutionsCount = 21)))
        }
    }

    @Test
    fun `test onNext - after changing selection - verify correct UiEvent emitted`() = runTest {
        mockkObject(BoardSize.FiveByFive)
        every { BoardSize.FiveByFive.size } returns 911
        every { BoardSize.FiveByFive.allSolutionsCount } returns 654
        val sut = initSut()
        sut.uiState.test uiState@{
            sut.uiEvent.test uiEvent@{
                assertThat(this@uiState.awaitItem()).isEqualTo(defaultUiState)
                sut.onBoardSizePicked(boardSize = BoardSize.FiveByFive)
                assertThat(this@uiState.awaitItem())
                    .isEqualTo(UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.FiveByFive)))

                sut.onNext()
                assertThat(this@uiEvent.awaitItem())
                    .isEqualTo(InitialViewModel.UiEvent.NavigateToGamePlay(route = GameRoute.GamePlay(boardSize = 911, allSolutionsCount = 654)))
            }
        }
    }

    private fun initSut() = InitialViewModel()

    private companion object {
        val defaultUiState = UiState(boardSizePickerState = BoardSizePickerState(isExpanded = false, selectedOption = BoardSize.EightByEight))
    }
}