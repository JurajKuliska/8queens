package com.jurajkuliska.eightqueens.game.domain.handler

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.PiecePlacementResult
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
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasPiece = true)),
                    ),
                )
            )
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][Q]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(column3 = getBoardRow2()[3].copy(hasPiece = true)),
                        getBoardRow1(column0 = getBoardRow1()[0].copy(hasPiece = true)),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasPiece = true)),
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
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(PiecePlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(PiecePlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [Q][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(PiecePlacementResult.Success.Removed)
            /**    0  1  2  3
             *  0 [ ][ ][Q][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasPiece = true)),
                    ),
                )
            )
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(PiecePlacementResult.Success.Removed)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][ ]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][ ][ ][ ]
             *  1 [ ][ ][ ][ ]
             *  2 [ ][ ][ ][Q]
             *  3 [ ][Q][ ][ ]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(column3 = getBoardRow2()[3].copy(hasPiece = true)),
                        getBoardRow1(),
                        getBoardRow0(),
                    ),
                )
            )
        }
    }

    @Test
    fun `test board conflicting queen - 1 queen on the board`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(PiecePlacementResult.Success.Added)
            /**    0  1  2  3
             *  0 [ ][X][ ][ ]
             *  1 [ ][X][ ][X]
             *  2 [X][X][X][ ]
             *  3 [X][Q][X][X]
             */
            assertThat(awaitItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
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
                assertWithMessage("Conflict verification failed for coordinates $coordinatesPair")
                    .that(sut.placePiece(coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second)))
                    .isEqualTo(PiecePlacementResult.Conflict(conflictingCoordinates = setOf(Coordinates(rowIndex = 3, columnIndex = 1))))
            }
            expectNoEvents()
        }
    }

    @Test
    fun `test board conflicting queen - 2 queens on the board`() = runTest {
        val sut = BoardStateHandlerImpl(
            boardSize = 4,
            boardDefinition = getBoardDefinitionSize4()
        )
        sut.board.test {
            assertThat(awaitItem()).isEqualTo(boardStateDefault)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(PiecePlacementResult.Success.Added)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(PiecePlacementResult.Success.Added)

            /**    0  1  2  3
             *  0 [X][X][Q][X]
             *  1 [ ][X][X][X]
             *  2 [X][X][X][ ]
             *  3 [X][Q][X][X]
             */
            assertThat(expectMostRecentItem()).isEqualTo(
                BoardState(
                    board = listOf(
                        getBoardRow3(column1 = getBoardRow3()[1].copy(hasPiece = true)),
                        getBoardRow2(),
                        getBoardRow1(),
                        getBoardRow0(column2 = getBoardRow0()[2].copy(hasPiece = true)),
                    ),
                )
            )

            // conflicts for both Queens
            listOf(
                0 to 1,
                1 to 1,
                1 to 3,
                2 to 0,
                2 to 2,
                3 to 2,
            ).forEach { coordinatesPair ->
                assertWithMessage("Conflict verification failed for coordinates $coordinatesPair")
                    .that(sut.placePiece(coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second)))
                    .isEqualTo(
                        PiecePlacementResult.Conflict(
                            conflictingCoordinates = setOf(Coordinates(rowIndex = 3, columnIndex = 1), Coordinates(rowIndex = 0, columnIndex = 2))
                        )
                    )
            }

            // Conflicts only for queen on coordinates [0,2]
            listOf(
                0 to 0,
                0 to 3,
                1 to 2,
            ).forEach { coordinatesPair ->
                assertWithMessage("Conflict verification failed for coordinates $coordinatesPair")
                    .that(sut.placePiece(coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second)))
                    .isEqualTo(
                        PiecePlacementResult.Conflict(conflictingCoordinates = setOf(Coordinates(rowIndex = 0, columnIndex = 2)))
                    )
            }

            // Conflicts only for queen on coordinates [3,1]
            listOf(
                2 to 1,
                3 to 0,
                3 to 3,
            ).forEach { coordinatesPair ->
                assertWithMessage("Conflict verification failed for coordinates $coordinatesPair")
                    .that(sut.placePiece(coordinates = Coordinates(rowIndex = coordinatesPair.first, columnIndex = coordinatesPair.second)))
                    .isEqualTo(
                        PiecePlacementResult.Conflict(conflictingCoordinates = setOf(Coordinates(rowIndex = 3, columnIndex = 1)))
                    )
            }
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
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 3, columnIndex = 1))).isEqualTo(PiecePlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 1, columnIndex = 0))).isEqualTo(PiecePlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 0, columnIndex = 2))).isEqualTo(PiecePlacementResult.Success.Added)
            skipItems(1)
            assertThat(sut.placePiece(coordinates = Coordinates(rowIndex = 2, columnIndex = 3))).isEqualTo(PiecePlacementResult.Success.Added)
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