package com.jurajkuliska.eightqueens.game.domain.usecase

internal interface GetColumnNotationUseCase {
    operator fun invoke(columnIndex: Int): Char
}

internal class GetColumnNotationUseCaseImpl : GetColumnNotationUseCase {

    override operator fun invoke(columnIndex: Int): Char = ALPHABET[columnIndex.mod(ALPHABET.length)]

    private companion object {
        const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
}