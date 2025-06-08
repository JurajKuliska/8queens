package com.jurajkuliska.eightqueens.presentation.model

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class BoardSizeTest {

    @Test
    @Parameters(method = "getSizeTestData")
    fun `test size of BoardSize enum`(testData: TestDataHolder) {
        assertThat(testData.input.size).isEqualTo(testData.expectedResult)
    }

    @Test
    @Parameters(method = "getTitleTestData")
    fun `test title of BoardSize enum`(testData: TestDataHolder) {
        assertThat(testData.input.title).isEqualTo(testData.expectedResult)
    }

    @Test
    @Parameters(method = "getAllSolutionsCountTestData")
    fun `test allSolutionsCount of BoardSize enum`(testData: TestDataHolder) {
        assertThat(testData.input.allSolutionsCount).isEqualTo(testData.expectedResult)
    }

    fun getSizeTestData() = BoardSize.entries.map { entry ->
        when (entry) {
            BoardSize.FourByFour -> 4
            BoardSize.FiveByFive -> 5
            BoardSize.SixBySix -> 6
            BoardSize.SevenBySeven -> 7
            BoardSize.EightByEight -> 8
        }.let {
            TestDataHolder(input = entry, expectedResult = it)
        }
    }

    fun getTitleTestData() = BoardSize.entries.map { entry ->
        when (entry) {
            BoardSize.FourByFour -> R.string.initial_screen_board_size_4_by_4
            BoardSize.FiveByFive -> R.string.initial_screen_board_size_5_by_5
            BoardSize.SixBySix -> R.string.initial_screen_board_size_6_by_6
            BoardSize.SevenBySeven -> R.string.initial_screen_board_size_7_by_7
            BoardSize.EightByEight -> R.string.initial_screen_board_size_8_by_8
        }.let {
            TestDataHolder(input = entry, expectedResult = it)
        }
    }

    fun getAllSolutionsCountTestData() = BoardSize.entries.map { entry ->
        when (entry) {
            BoardSize.FourByFour -> 2
            BoardSize.FiveByFive -> 10
            BoardSize.SixBySix -> 4
            BoardSize.SevenBySeven -> 40
            BoardSize.EightByEight -> 92
        }.let {
            TestDataHolder(input = entry, expectedResult = it)
        }
    }

    data class TestDataHolder(
        val input: BoardSize,
        val expectedResult: Int,
    )
}