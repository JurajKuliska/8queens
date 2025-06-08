package com.jurajkuliska.eightqueens.game.domain.model

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow0
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow1
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow2
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow3
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class BoardStateTest {

    @Test
    @Parameters(method = "getTotalQueensToPlaceTestData")
    fun `test totalQueensToPlace and queensLeft`(testData: TestDataHolder) {
        BoardState(board = testData.inputBoard).run {
            assertThat(totalQueensToPlace).isEqualTo(testData.expectedTotalQueensToPlace)
            assertThat(queensLeft).isEqualTo(testData.expectedQueensLeft)
        }
    }

    fun getTotalQueensToPlaceTestData() = listOf(
        TestDataHolder(inputBoard = listOf(), expectedTotalQueensToPlace = 0, expectedQueensLeft = 0),
        TestDataHolder(
            inputBoard = listOf(
                getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                getBoardRow2(),
                getBoardRow1(column0 = getBoardRow1()[0].copy(hasQueen = true)),
                getBoardRow0(),
            ),
            expectedTotalQueensToPlace = 4,
            expectedQueensLeft = 2,
        ),
        TestDataHolder(
            inputBoard = listOf(
                getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                getBoardRow2(column3 = getBoardRow2()[3].copy(hasQueen = true)),
                getBoardRow1(column0 = getBoardRow1()[0].copy(hasQueen = true)),
                getBoardRow0(column2 = getBoardRow0()[2].copy(hasQueen = true)),
            ),
            expectedTotalQueensToPlace = 4,
            expectedQueensLeft = 0,
        ),
        TestDataHolder(
            inputBoard = listOf(
                getBoardRow3(column1 = getBoardRow3()[1].copy(hasQueen = true)),
                getBoardRow2(),
                getBoardRow0(),
            ),
            expectedTotalQueensToPlace = 3,
            expectedQueensLeft = 2,
        ),
        TestDataHolder(
            inputBoard = listOf(
                getBoardRow3(),
                getBoardRow3(),
                getBoardRow3(),
                getBoardRow3(),
                getBoardRow3(),
                getBoardRow2(),
                getBoardRow0(),
            ),
            expectedTotalQueensToPlace = 7,
            expectedQueensLeft = 7,
        ),
    )

    data class TestDataHolder(
        val inputBoard: List<List<BoardTile>>,
        val expectedTotalQueensToPlace: Int,
        val expectedQueensLeft: Int,
    )
}