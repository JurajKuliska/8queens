package com.jurajkuliska.eightqueens

import com.jurajkuliska.eightqueens.app.di.appModule
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

internal class KoinModulesTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        appModule.verify(
            extraTypes = listOf(
                GameRoute.GamePlay::class,
            )
        )
    }
}