package com.jurajkuliska.eightqueens.game.presentation.model

import androidx.annotation.StringRes
import com.jurajkuliska.eightqueens.game.presentation.R
import kotlinx.serialization.Serializable

@Serializable
internal enum class BoardSize(
    val size: Int,
    @StringRes val title: Int,
    val allSolutionsCount: Int,
) {
    FourByFour(size = 4, title = R.string.initial_screen_board_size_4_by_4, allSolutionsCount = 2),
    FiveByFive(size = 5, title = R.string.initial_screen_board_size_5_by_5, allSolutionsCount = 10),
    SixBySix(size = 6, title = R.string.initial_screen_board_size_6_by_6, allSolutionsCount = 4),
    SevenBySeven(size = 7, title = R.string.initial_screen_board_size_7_by_7, allSolutionsCount = 40),
    EightByEight(size = 8, title = R.string.initial_screen_board_size_8_by_8, allSolutionsCount = 92),
}