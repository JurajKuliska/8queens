package com.jurajkuliska.eightqueens.game.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class CreateBoardUseCaseImplTest {

    private val getColumnNotationUseCaseMock = mockk<GetColumnNotationUseCase>()

    @After
    fun tearDown() {
        confirmVerified(getColumnNotationUseCaseMock)
    }

    @Parameters(method = "getTestData")
    @Test
    fun test_invoke_verify_correct_result(testData: TestDataHolder) {
        testData.columnNotationMock.forEach {
            every { getColumnNotationUseCaseMock(columnIndex = it.first) } returns it.second
        }
        val sut = initSut()
        assertThat(sut(boardSize = testData.inputBoardSize)).isEqualTo(testData.expectedResult)

        testData.columnNotationMock.forEach {
            verify(exactly = 1) { getColumnNotationUseCaseMock(columnIndex = it.first) }
        }
    }

    fun getTestData() = listOf<TestDataHolder>(
        TestDataHolder(inputBoardSize = 0, columnNotationMock = emptyList(), expectedResult = emptyList()),
        TestDataHolder(inputBoardSize = -1, columnNotationMock = emptyList(), expectedResult = emptyList()),
        TestDataHolder(
            inputBoardSize = 1,
            columnNotationMock = listOf(0 to 'A'),
            expectedResult = listOf(
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), rowNotation = "1", columnNotation = "A")
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 2,
            columnNotationMock = listOf(0 to 'A', 1 to 'B'),
            expectedResult = listOf(
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 0), rowNotation = "2", columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 1), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), rowNotation = "1", columnNotation = "A"),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 1), rowNotation = null, columnNotation = "B")
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 3,
            columnNotationMock = listOf(0 to 'A', 1 to 'B', 2 to 'C'),
            expectedResult = listOf(
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 0), rowNotation = "3", columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 1), rowNotation = null, columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 2), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 0), rowNotation = "2", columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 1), rowNotation = null, columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 2), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), rowNotation = "1", columnNotation = "A"),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 1), rowNotation = null, columnNotation = "B"),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 2), rowNotation = null, columnNotation = "C")
                )
            ),
        ),
        TestDataHolder(
            inputBoardSize = 4,
            columnNotationMock = listOf(0 to 'A', 1 to 'B', 2 to 'C', 3 to 'X'),
            expectedResult = listOf(
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 0), rowNotation = "4", columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 1), rowNotation = null, columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 2), rowNotation = null, columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 3), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 0), rowNotation = "3", columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 1), rowNotation = null, columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 2), rowNotation = null, columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 3), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 0), rowNotation = "2", columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 1), rowNotation = null, columnNotation = null),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 2), rowNotation = null, columnNotation = null),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 3), rowNotation = null, columnNotation = null),
                ),
                listOf(
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), rowNotation = "1", columnNotation = "A"),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 1), rowNotation = null, columnNotation = "B"),
                    createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 2), rowNotation = null, columnNotation = "C"),
                    createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 3), rowNotation = null, columnNotation = "X")
                )
            ),
        ),
    )

    data class TestDataHolder(
        val inputBoardSize: Int,
        val columnNotationMock: List<Pair<Int, Char>>,
        val expectedResult: List<List<BoardTile>>,
    )

    private fun createWhiteBoardTile(
        coordinates: Coordinates,
        rowNotation: String?,
        columnNotation: String?,
    ) = BoardTile(
        coordinates = coordinates,
        rowNotation = rowNotation,
        columnNotation = columnNotation,
        isWhite = true,
        hasQueen = false,
    )

    private fun createBlackBoardTile(
        coordinates: Coordinates,
        rowNotation: String?,
        columnNotation: String?,
    ) = BoardTile(
        coordinates = coordinates,
        rowNotation = rowNotation,
        columnNotation = columnNotation,
        isWhite = false,
        hasQueen = false,
    )

    private fun initSut() = CreateBoardUseCaseImpl(
        getColumnNotationUseCase = getColumnNotationUseCaseMock,
    )
}