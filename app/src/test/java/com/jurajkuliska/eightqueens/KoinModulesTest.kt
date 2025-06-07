package com.jurajkuliska.eightqueens

import com.jurajkuliska.eightqueens.app.di.appModule
import org.junit.Test
import org.koin.test.verify.verify

internal class KoinModulesTest {

    @Test
    fun checkAllModules() {
        appModule.verify()
    }
}