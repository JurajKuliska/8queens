package com.jurajkuliska.eightqueens.game.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import io.mockk.every
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class CreateBoardUseCaseImplTest {

    private val getColumnNotationUseCaseMock = mockk<GetColumnNotationUseCase>()

    @Parameters(method = "getTestData")
    @Test
    fun test_invoke_verify_correct_result(testData: TestDataHolder) {
        testData.columnNotationMock.forEach {
            every { getColumnNotationUseCaseMock(columnIndex = it.first) } returns it.second
        }
        val sut = initSut()
        assertThat(sut(boardSize = testData.inputBoardSize)).isEqualTo(testData.expectedResult)
    }

    fun getTestData() = listOf<TestDataHolder>(
        TestDataHolder(inputBoardSize = 0, columnNotationMock = emptyList(), expectedResult = emptyList()),
        TestDataHolder(inputBoardSize = -1, columnNotationMock = emptyList(), expectedResult = emptyList()),
        TestDataHolder(
            inputBoardSize = 1,
            columnNotationMock = listOf(0 to 'A'),
            expectedResult = listOf(
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "1",
                        columnNotation = "A",
                    )
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 2,
            columnNotationMock = listOf(0 to 'A', 1 to 'B'),
            expectedResult = listOf(
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "2",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 1),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 0),
                        isWhite = false,
                        rowNotation = "1",
                        columnNotation = "A",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 1),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = "B",
                    )
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 3,
            columnNotationMock = listOf(0 to 'A', 1 to 'B', 2 to 'C'),
            expectedResult = listOf(
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "3",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 1),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 2),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 0),
                        isWhite = false,
                        rowNotation = "2",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 1),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 2),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "1",
                        columnNotation = "A",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 1),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = "B",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 2),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = "C",
                    )
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 4,
            columnNotationMock = listOf(0 to 'A', 1 to 'B', 2 to 'C', 3 to 'X'),
            expectedResult = listOf(
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 3, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "4",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 3, columnIndex = 1),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 3, columnIndex = 2),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 3, columnIndex = 3),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 0),
                        isWhite = false,
                        rowNotation = "3",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 1),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 2),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 2, columnIndex = 3),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 0),
                        isWhite = true,
                        rowNotation = "2",
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 1),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 2),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 1, columnIndex = 3),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = null,
                    ),
                ),
                listOf(
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 0),
                        isWhite = false,
                        rowNotation = "1",
                        columnNotation = "A",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 1),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = "B",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 2),
                        isWhite = false,
                        rowNotation = null,
                        columnNotation = "C",
                    ),
                    BoardTile(
                        coordinates = Coordinates(rowIndex = 0, columnIndex = 3),
                        isWhite = true,
                        rowNotation = null,
                        columnNotation = "X",
                    )
                )
            ),
        ),
    )

    data class TestDataHolder(
        val inputBoardSize: Int,
        val columnNotationMock: List<Pair<Int, Char>>,
        val expectedResult: List<List<BoardTile>>,
    )

    private fun initSut() = CreateBoardUseCaseImpl(
        getColumnNotationUseCase = getColumnNotationUseCaseMock,
    )
}