package com.jurajkuliska.eightqueens.presentation.screens.gameplay

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.PiecePlacementResult
import com.jurajkuliska.eightqueens.game.domain.usecase.GetBoardStateHandlerUseCase
import com.jurajkuliska.eightqueens.game.presentation.model.BoardStateUi
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.game.presentation.screens.gameplay.GamePlayViewModel
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.boardStateDefault
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.boardStateUiDefault
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardDefinitionSize4
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardDefinitionSize4Ui
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow0
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow0Ui
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow1
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow1Ui
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow2
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow2Ui
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow3
import com.jurajkuliska.eightqueens.presentation.testdata.BoardStateTestData.getBoardRow3Ui
import com.jurajkuliska.eightqueens.presentation.testutils.MainDispatcherRule
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

internal class GamePlayViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getBoardStateHandlerUseCaseMock = mockk<GetBoardStateHandlerUseCase>()
    private val boardFlow = MutableStateFlow(boardStateDefault)
    private val boardStateHandlerMock = mockk<BoardStateHandler> {
        every { board } returns boardFlow
    }

    @After
    fun tearDown() {
        confirmVerified(
            getBoardStateHandlerUseCaseMock,
            boardStateHandlerMock
        )
    }

    @Test
    fun test_uiState_when_boardStateHandler_emits_different_values() = runTest {
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut()

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 89,
                )
            )

            boardFlow.value = BoardState(
                board = getBoardDefinitionSize4(
                    row3 = getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                    row2 = getBoardRow2(column3 = getBoardRow2()[3].copy(hasPiece = true)),
                    row1 = getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                    row0 = getBoardRow0(column2 = getBoardRow0()[2].copy(hasPiece = true)),
                ),
            )

            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = BoardStateUi(
                        board = getBoardDefinitionSize4Ui(
                            row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true)),
                            row2 = getBoardRow2Ui(column3 = getBoardRow2Ui()[3].copy(hasQueen = true)),
                            row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true)),
                            row0 = getBoardRow0Ui(column2 = getBoardRow0Ui()[2].copy(hasQueen = true)),
                        )
                    ),
                    isWin = true,
                    totalQueensToPlace = 4,
                    queensLeft = 0,
                    allSolutionsCount = 89,
                )
            )

            boardFlow.value = BoardState(
                board = getBoardDefinitionSize4(
                    row3 = getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                    row1 = getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                ),
            )

            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = BoardStateUi(
                        board = getBoardDefinitionSize4Ui(
                            row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true)),
                            row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true)),
                        )
                    ),
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 2,
                    allSolutionsCount = 89,
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_onTileTap_placeQueen_returns_Conflict() = runTest {
        val queenCoordinates = Coordinates(rowIndex = 1, columnIndex = 1)
        every {
            boardStateHandlerMock.placePiece(coordinates = queenCoordinates)
        } returns PiecePlacementResult.Conflict(conflictingCoordinates = setOf(Coordinates(rowIndex = 3, columnIndex = 1), queenCoordinates))
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut(navArgs = navArgs.copy(allSolutionsCount = 45))

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 45,
                )
            )

            boardFlow.value = BoardState(
                board = getBoardDefinitionSize4(
                    row3 = getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                    row1 = getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                ),
            )

            val uiStateWithoutErrors = GamePlayViewModel.UiState(
                boardState = BoardStateUi(
                    board = getBoardDefinitionSize4Ui(
                        row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true)),
                        row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true)),
                    )
                ),
                isWin = false,
                totalQueensToPlace = 4,
                queensLeft = 2,
                allSolutionsCount = 45,
            )
            val uiStateWithErrors = GamePlayViewModel.UiState(
                boardState = BoardStateUi(
                    board = getBoardDefinitionSize4Ui(
                        row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true, isError = true)),
                        row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true), column1 = getBoardRow1Ui()[1].copy(isError = true)),
                    )
                ),
                isWin = false,
                totalQueensToPlace = 4,
                queensLeft = 2,
                allSolutionsCount = 45,
            )

            assertThat(awaitItem()).isEqualTo(uiStateWithoutErrors)

            sut.onTileTap(coordinates = queenCoordinates)
            repeat(3) {
                expectNoEvents()
                advanceTimeBy(199)
                expectNoEvents()
                advanceTimeBy(1)
                assertThat(awaitItem()).isEqualTo(uiStateWithErrors)
                advanceTimeBy(199)
                expectNoEvents()
                advanceTimeBy(1)
                assertThat(awaitItem()).isEqualTo(uiStateWithoutErrors)
            }
        }

        verify(exactly = 1) {
            boardStateHandlerMock.placePiece(coordinates = queenCoordinates)
        }
    }

    @Test
    fun test_onTileTap_afterPlacingQueenWithError_cancels_previousError() =
        testCancelPreviousErrorAfterFunctionCall {
            val coordinates = Coordinates(rowIndex = 2, columnIndex = 2)
            every { boardStateHandlerMock.placePiece(coordinates = coordinates) } returns PiecePlacementResult.Success.Added
            it.onTileTap(coordinates = coordinates)
            verify(exactly = 1) { boardStateHandlerMock.placePiece(coordinates = coordinates) }
        }

    @Test
    fun test_onResetClick_afterPlacingQueenWithError_cancels_previousError() =
        testCancelPreviousErrorAfterFunctionCall {
            every { boardStateHandlerMock.reset() } just Runs
            it.onResetClick()
            verify(exactly = 1) { boardStateHandlerMock.reset() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun testCancelPreviousErrorAfterFunctionCall(function: (GamePlayViewModel) -> Unit) = runTest {
        val queenCoordinates = Coordinates(rowIndex = 1, columnIndex = 1)
        every {
            boardStateHandlerMock.placePiece(coordinates = queenCoordinates)
        } returns PiecePlacementResult.Conflict(conflictingCoordinates = setOf(Coordinates(rowIndex = 3, columnIndex = 1), queenCoordinates))
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut(navArgs = navArgs.copy(allSolutionsCount = 45))

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 45,
                )
            )

            boardFlow.value = BoardState(
                board = getBoardDefinitionSize4(
                    row3 = getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                    row1 = getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                ),
            )

            val uiStateWithoutErrors = GamePlayViewModel.UiState(
                boardState = BoardStateUi(
                    board = getBoardDefinitionSize4Ui(
                        row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true)),
                        row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true)),
                    )
                ),
                isWin = false,
                totalQueensToPlace = 4,
                queensLeft = 2,
                allSolutionsCount = 45,
            )
            val uiStateWithErrors = GamePlayViewModel.UiState(
                boardState = BoardStateUi(
                    board = getBoardDefinitionSize4Ui(
                        row3 = getBoardRow3Ui(column1 = getBoardRow3Ui()[1].copy(hasQueen = true, isError = true)),
                        row1 = getBoardRow1Ui(column0 = getBoardRow1Ui()[0].copy(hasQueen = true), column1 = getBoardRow1Ui()[1].copy(isError = true)),
                    )
                ),
                isWin = false,
                totalQueensToPlace = 4,
                queensLeft = 2,
                allSolutionsCount = 45,
            )

            assertThat(awaitItem()).isEqualTo(uiStateWithoutErrors)

            sut.onTileTap(coordinates = queenCoordinates)
            expectNoEvents()
            advanceTimeBy(199)
            expectNoEvents()
            advanceTimeBy(1)
            assertThat(awaitItem()).isEqualTo(uiStateWithErrors)
            function(sut)
            assertThat(awaitItem()).isEqualTo(uiStateWithoutErrors)

            verify(exactly = 1) {
                boardStateHandlerMock.placePiece(coordinates = queenCoordinates)
            }
        }
    }

    @Test
    fun test_onTileTap_placeQueen_returns_Success_Added() = runTest {
        val coordinatesMock = mockk<Coordinates>()
        every { boardStateHandlerMock.placePiece(coordinates = coordinatesMock) } returns PiecePlacementResult.Success.Added
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut()

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 89,
                )
            )

            sut.onTileTap(coordinates = coordinatesMock)

            expectNoEvents()
        }

        verify(exactly = 1) {
            boardStateHandlerMock.placePiece(coordinates = coordinatesMock)
        }
    }

    @Test
    fun test_onTileTap_placeQueen_returns_Success_Removed() = runTest {
        val coordinatesMock = mockk<Coordinates>()
        every { boardStateHandlerMock.placePiece(coordinates = coordinatesMock) } returns PiecePlacementResult.Success.Removed
        every { getBoardStateHandlerUseCaseMock(boardSize = 5) } returns boardStateHandlerMock
        val sut = initSut(navArgs.copy(boardSize = 5))

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 89,
                )
            )

            sut.onTileTap(coordinates = coordinatesMock)

            expectNoEvents()
        }

        verify(exactly = 1) {
            boardStateHandlerMock.placePiece(coordinates = coordinatesMock)
        }
    }

    @Test
    fun test_onResetClick() = runTest {
        every { getBoardStateHandlerUseCaseMock(boardSize = 1) } returns boardStateHandlerMock
        every { boardStateHandlerMock.reset() } just Runs
        val sut = initSut(navArgs = GameRoute.GamePlay(boardSize = 1, allSolutionsCount = 3))

        sut.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                GamePlayViewModel.UiState(
                    boardState = boardStateUiDefault,
                    isWin = false,
                    totalQueensToPlace = 4,
                    queensLeft = 4,
                    allSolutionsCount = 3,
                )
            )
            sut.onResetClick()
            expectNoEvents()
        }

        verify(exactly = 1) {
            boardStateHandlerMock.reset()
        }
    }

    @Test
    fun test_onPickDifferentBoardClick_emits_navigateBack_uiEvent() = runTest {
        every { getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize) } returns boardStateHandlerMock
        val sut = initSut()
        sut.uiEvent.test {
            sut.onPickDifferentBoardClick()
            assertThat(awaitItem()).isEqualTo(GamePlayViewModel.UiEvent.RestartGame)
        }
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
    ).also {
        verify(exactly = 1) {
            getBoardStateHandlerUseCaseMock(boardSize = navArgs.boardSize)
            boardStateHandlerMock.board
        }
    }

    private companion object {
        val navArgs = GameRoute.GamePlay(boardSize = 4, allSolutionsCount = 89)
    }
}