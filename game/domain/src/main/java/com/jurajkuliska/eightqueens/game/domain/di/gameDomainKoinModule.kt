package com.jurajkuliska.eightqueens.game.domain.di

import com.jurajkuliska.eightqueens.game.domain.usecase.CreateBoardUseCase
import com.jurajkuliska.eightqueens.game.domain.usecase.CreateBoardUseCaseImpl
import com.jurajkuliska.eightqueens.game.domain.usecase.GetColumnNotationUseCase
import com.jurajkuliska.eightqueens.game.domain.usecase.GetColumnNotationUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val gameDomainKoinModule = module {
    factoryOf(::CreateBoardUseCaseImpl) bind CreateBoardUseCase::class
    factoryOf(::GetColumnNotationUseCaseImpl) bind GetColumnNotationUseCase::class
}
