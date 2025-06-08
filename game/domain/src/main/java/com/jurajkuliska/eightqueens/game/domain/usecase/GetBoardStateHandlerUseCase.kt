package com.jurajkuliska.eightqueens.game.domain.usecase

import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandlerImpl

interface GetBoardStateHandlerUseCase {
    operator fun invoke(boardSize: Int): BoardStateHandler
}

internal class GetBoardStateHandlerUseCaseImpl(
    private val createBoardUseCase: CreateBoardUseCase,
) : GetBoardStateHandlerUseCase {
    override operator fun invoke(boardSize: Int): BoardStateHandler = BoardStateHandlerImpl(
        boardSize = boardSize,
        boardDefinition = createBoardUseCase(boardSize = boardSize),
    )
}

