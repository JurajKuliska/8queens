package com.jurajkuliska.eightqueens.game.domain.usecase

import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandlerImpl
import com.jurajkuliska.eightqueens.game.domain.model.GameType

interface GetBoardStateHandlerUseCase {
    operator fun invoke(gameType: GameType): BoardStateHandler
}

internal class GetBoardStateHandlerUseCaseImpl(
    private val createBoardUseCase: CreateBoardUseCase,
) : GetBoardStateHandlerUseCase {

    override operator fun invoke(gameType: GameType): BoardStateHandler = BoardStateHandlerImpl(
        gameType = gameType,
        boardDefinition = createBoardUseCase(boardSize = gameType.boardSize),
    )
}

