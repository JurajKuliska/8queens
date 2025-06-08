package com.jurajkuliska.eightqueens.game.domain.handler

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.Queen
import com.jurajkuliska.eightqueens.game.domain.model.QueenPlacementResult
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardDefinitionSize4
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow0
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow1
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow2
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow3
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class BoardStateHandlerImplTest {

    @Test
    fun `test board when board has no queens`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
        }
    }

    @Test
    fun `test board adding queen`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasQueen = true)),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasQueen = true)),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasQueen = true)),
                    ),
                )
            )
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(QueenPlacementResult.Success.AddedAndWin)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][Q]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(column3 = getBoardRow2()[3].copy(hasQueen = true)),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasQueen = true)),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasQueen = true)),
                    ),
                )
            )
        }
    }

    @Test
    fun `test board removing queen`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(QueenPlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(QueenPlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(QueenPlacementResult.Success.Removed)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasQueen = true)),
                    ),
                )
            )
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(QueenPlacementResult.Success.Removed)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][Q]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(column3 = getBoardRow2()[3].copy(hasQueen = true)),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
        }
    }

    @Test
    fun `test board conflicting queen`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(QueenPlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][X][ ][ ]
             *  1 [ ][X][ ][X]
             *  2 [X][X][X][ ]
             *  3 [X][Q][X][X]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
            listOf(
                2 to 0,
                3 to 0,
                0 to 1,
                1 to 1,
                2 to 1,
                2 to 2,
                3 to 2,
                1 to 3,
                3 to 3,
            ).forEach { coordinatesPair ->
                assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second))).isEqualTo(
                    QueenPlacementResult.Conflict(
                        queen = Queen(
                            coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second),
                            boardSize = 4
                        )
                    )
                )
            }
            expectNoEvents()
        }
    }

    @Test
    fun `test board reset`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(QueenPlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(QueenPlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(QueenPlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placeQueen(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(QueenPlacementResult.Success.AddedAndWin)
            skipItems(1)
            sut.reset()
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
        }
    }

    private companion object {
        val boardStateDefault = BoardState(
            board = listOf(
                getBoardRow3(),
                getBoardRow2(),
                getBoardRow1(),
                getBoardRow0(),
            )
        )
    }
}