package com.jurajkuliska.eightqueens.app.di

import com.jurajkuliska.eightqueens.game.presentation.di.gamePresentationKoinModule
import org.koin.dsl.module

internal val appModule = module {
    includes(
        gamePresentationKoinModule,
    )
}