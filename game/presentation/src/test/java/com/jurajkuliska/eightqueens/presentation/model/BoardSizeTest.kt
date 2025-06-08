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
    @Parameters(method = "getSizeAndTitleTestData")
    fun `test size and title of BoardSize enum`(testData: TestDataHolder) {
        testData.input.run {
            assertThat(size).isEqualTo(testData.expectedSize)
            assertThat(title).isEqualTo(testData.expectedTitleResId)
        }
    }

    fun getSizeAndTitleTestData() = BoardSize.entries.map { entry ->
        when (entry) {
            BoardSize.FourByFour -> 4 to R.string.initial_screen_board_size_4_by_4
            BoardSize.FiveByFive -> 5 to R.string.initial_screen_board_size_5_by_5
            BoardSize.SixBySix -> 6 to R.string.initial_screen_board_size_6_by_6
            BoardSize.SevenBySeven -> 7 to R.string.initial_screen_board_size_7_by_7
            BoardSize.EightByEight -> 8 to R.string.initial_screen_board_size_8_by_8
        }.let {
            TestDataHolder(input = entry, expectedSize = it.first, expectedTitleResId = it.second)
        }
    }

    data class TestDataHolder(
        val input: BoardSize,
        val expectedSize: Int,
        val expectedTitleResId: Int,
    )
}