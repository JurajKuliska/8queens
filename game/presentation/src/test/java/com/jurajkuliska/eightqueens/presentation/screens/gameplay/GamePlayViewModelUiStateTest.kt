package com.jurajkuliska.eightqueens.presentation.screens.gameplay

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.presentation.screens.gameplay.GamePlayViewModel
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class GamePlayViewModelUiStateTest {

    @Test
    @Parameters(method = "getIsRestartGameButtonEnabledTrueTestData")
    fun `test isRestartGameButtonEnabled`(testData: TestDataHolder) {
        val sut = GamePlayViewModel.UiState(
            totalQueensToPlace = testData.totalQueensToPlace,
            queensLeft = testData.queensLeft,
            boardState = mockk(),
            allSolutionsCount = 54,
            isWin = false,
        )
        assertThat(sut.isRestartGameButtonEnabled).isEqualTo(testData.expectedResult)
    }

    fun getIsRestartGameButtonEnabledTrueTestData() = listOf(
        TestDataHolder(totalQueensToPlace = 0, queensLeft = 4, expectedResult = false),
        TestDataHolder(totalQueensToPlace = 4, queensLeft = 0, expectedResult = true),
        TestDataHolder(totalQueensToPlace = 1, queensLeft = 2, expectedResult = false),
        TestDataHolder(totalQueensToPlace = 3, queensLeft = 2, expectedResult = true),
    )

    data class TestDataHolder(
        val totalQueensToPlace: Int,
        val queensLeft: Int,
        val expectedResult: Boolean,
    )
}