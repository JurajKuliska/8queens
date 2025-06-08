package com.jurajkuliska.eightqueens.game.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandlerImpl
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import io.mockk.EqMatcher
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import io.mockk.verify
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class GetBoardStateHandlerUseCaseImplTest {

    private val createBoardUseCaseMock = mockk<CreateBoardUseCase>()

    @After
    fun tearDown() {
        confirmVerified(createBoardUseCaseMock)
        unmockkAll()
    }

    @Test
    @Parameters(value = ["0", "1", "5"])
    fun `test invoke - verify correct parameters passed and correct result returned`(boardSize: Int) {
        mockkConstructor(BoardStateHandlerImpl::class)
        val boardDefinitionMock = mockk<List<List<BoardTile>>>()
        every { createBoardUseCaseMock(boardSize = boardSize) } returns boardDefinitionMock
        val sut = initSut()
        assertThat(sut(boardSize = boardSize)).isInstanceOf(BoardStateHandlerImpl::class.java)

        verify(exactly = 1) { createBoardUseCaseMock(boardSize = boardSize) }
        verify(exactly = 1) { constructedWith<BoardStateHandlerImpl>(EqMatcher(boardSize), EqMatcher(boardDefinitionMock)) }
    }

    private fun initSut() = GetBoardStateHandlerUseCaseImpl(
        createBoardUseCase = createBoardUseCaseMock,
    )
}