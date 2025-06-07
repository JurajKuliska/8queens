package com.jurajkuliska.eightqueens.game.presentation.di

import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val gamePresentationKoinModule = module {
    viewModelOf(::InitialViewModel)
}