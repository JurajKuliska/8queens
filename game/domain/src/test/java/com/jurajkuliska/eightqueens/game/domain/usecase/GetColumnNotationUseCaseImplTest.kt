package com.jurajkuliska.eightqueens.game.domain.usecase

import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class GetColumnNotationUseCaseImplTest {

    @Parameters(method = "getTestData")
    @Test
    fun test_invoke_verify_correct_result(testData: TestDataHolder) = {
        val sut = initSut()
        assertThat(sut(columnIndex = testData.inputColumnIndex)).isEqualTo(testData.expectedResult)
    }

    fun getTestData() = listOf(
        TestDataHolder(inputColumnIndex = -1, expectedResult = 'Z'),
        TestDataHolder(inputColumnIndex = -2, expectedResult = 'Y'),
        TestDataHolder(inputColumnIndex = -23, expectedResult = 'B'),
        TestDataHolder(inputColumnIndex = -24, expectedResult = 'A'),
        TestDataHolder(inputColumnIndex = -25, expectedResult = 'Z'),
        TestDataHolder(inputColumnIndex = 0, expectedResult = 'A'),
        TestDataHolder(inputColumnIndex = 1, expectedResult = 'B'),
        TestDataHolder(inputColumnIndex = 2, expectedResult = 'C'),
        TestDataHolder(inputColumnIndex = 3, expectedResult = 'D'),
        TestDataHolder(inputColumnIndex = 4, expectedResult = 'E'),
        TestDataHolder(inputColumnIndex = 5, expectedResult = 'F'),
        TestDataHolder(inputColumnIndex = 6, expectedResult = 'G'),
        TestDataHolder(inputColumnIndex = 7, expectedResult = 'H'),
        TestDataHolder(inputColumnIndex = 8, expectedResult = 'I'),
        TestDataHolder(inputColumnIndex = 9, expectedResult = 'J'),
        TestDataHolder(inputColumnIndex = 10, expectedResult = 'K'),
        TestDataHolder(inputColumnIndex = 11, expectedResult = 'L'),
        TestDataHolder(inputColumnIndex = 12, expectedResult = 'M'),
        TestDataHolder(inputColumnIndex = 13, expectedResult = 'N'),
        TestDataHolder(inputColumnIndex = 14, expectedResult = 'O'),
        TestDataHolder(inputColumnIndex = 15, expectedResult = 'P'),
        TestDataHolder(inputColumnIndex = 16, expectedResult = 'Q'),
        TestDataHolder(inputColumnIndex = 17, expectedResult = 'R'),
        TestDataHolder(inputColumnIndex = 18, expectedResult = 'S'),
        TestDataHolder(inputColumnIndex = 19, expectedResult = 'T'),
        TestDataHolder(inputColumnIndex = 20, expectedResult = 'U'),
        TestDataHolder(inputColumnIndex = 21, expectedResult = 'V'),
        TestDataHolder(inputColumnIndex = 22, expectedResult = 'W'),
        TestDataHolder(inputColumnIndex = 23, expectedResult = 'X'),
        TestDataHolder(inputColumnIndex = 24, expectedResult = 'Y'),
        TestDataHolder(inputColumnIndex = 25, expectedResult = 'Z'),
        TestDataHolder(inputColumnIndex = 26, expectedResult = 'A'),
    )

    data class TestDataHolder(
        val inputColumnIndex: Int,
        val expectedResult: Char,
    )

    private fun initSut() = GetColumnNotationUseCaseImpl()
}