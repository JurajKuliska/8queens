package com.jurajkuliska.eightqueens.presentation.model

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.presentation.model.BoardTileUi
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class BoardTileUiTest {

    @Test
    @Parameters(method = "getShowQueenTestData")
    fun test_showQueen(testData: TestDataHolder) {
        val sut = BoardTileUi(
            coordinates = mockk(),
            isLight = false,
            rowNotation = null,
            columnNotation = null,
            isError = testData.isError,
            hasQueen = testData.hasQueen,
        )
        assertThat(sut.showQueen).isEqualTo(testData.expectedResult)
    }

    fun getShowQueenTestData() = listOf(
        TestDataHolder(isError = true, hasQueen = true, expectedResult = true),
        TestDataHolder(isError = true, hasQueen = false, expectedResult = true),
        TestDataHolder(isError = false, hasQueen = true, expectedResult = true),
        TestDataHolder(isError = false, hasQueen = false, expectedResult = false),
    )

    data class TestDataHolder(
        val isError: Boolean,
        val hasQueen: Boolean,
        val expectedResult: Boolean,
    )
}