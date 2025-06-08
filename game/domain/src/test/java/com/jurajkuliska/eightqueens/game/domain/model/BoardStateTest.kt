package com.jurajkuliska.eightqueens.game.domain.model

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow0
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow1
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow2
import com.jurajkuliska.eightqueens.game.domain.testdata.BoardTileTestData.getBoardRow3
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
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
        TestDataHolder(inputBoard = persistentListOf(), expectedTotalQueensToPlace = 0, expectedQueensLeft = 0),
        TestDataHolder(
            inputBoard = persistentListOf(
                getBoardRow3(
                    column1 = getBoardRow3()[1].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow2().toPersistentList(),
                getBoardRow1(
                    column0 = getBoardRow1()[0].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow0().toPersistentList(),
            ),
            expectedTotalQueensToPlace = 4,
            expectedQueensLeft = 2,
        ),
        TestDataHolder(
            inputBoard = persistentListOf(
                getBoardRow3(
                    column1 = getBoardRow3()[1].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow2(
                    column3 = getBoardRow2()[3].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow1(
                    column0 = getBoardRow1()[0].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow0(
                    column2 = getBoardRow0()[2].copy(hasQueen = true)
                ).toPersistentList(),
            ),
            expectedTotalQueensToPlace = 4,
            expectedQueensLeft = 0,
        ),
        TestDataHolder(
            inputBoard = persistentListOf(
                getBoardRow3(
                    column1 = getBoardRow3()[1].copy(hasQueen = true),
                ).toPersistentList(),
                getBoardRow2().toPersistentList(),
                getBoardRow0().toPersistentList(),
            ),
            expectedTotalQueensToPlace = 3,
            expectedQueensLeft = 2
        ),
        TestDataHolder(
            inputBoard = persistentListOf(
                getBoardRow3().toPersistentList(),
                getBoardRow3().toPersistentList(),
                getBoardRow3().toPersistentList(),
                getBoardRow3().toPersistentList(),
                getBoardRow3().toPersistentList(),
                getBoardRow2().toPersistentList(),
                getBoardRow0().toPersistentList(),
            ), expectedTotalQueensToPlace = 7, expectedQueensLeft = 7
        ),
    )

    data class TestDataHolder(
        val inputBoard: ImmutableList<ImmutableList<BoardTile>>,
        val expectedTotalQueensToPlace: Int,
        val expectedQueensLeft: Int,
    )
}