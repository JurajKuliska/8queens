package com.jurajkuliska.eightqueens.game.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class KnightTest {

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  6 [ ][ ][X][ ][X][ ][ ][ ]
     *  5 [ ][X][ ][ ][ ][X][ ][ ]
     *  4 [ ][ ][ ][N][ ][ ][ ][ ]
     *  3 [ ][X][ ][ ][ ][X][ ][ ]
     *  2 [ ][ ][X][ ][X][ ][ ][ ]
     *  1 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  0 [ ][ ][ ][ ][ ][ ][ ][ ]
     */

    @Test
    fun test_4_3() {
        val knight = Knight(coordinates = Coordinates(rowIndex = 4, columnIndex = 3), boardSize = 8)

        assertThat(knight.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 6, columnIndex = 2),
                Coordinates(rowIndex = 6, columnIndex = 4),
                Coordinates(rowIndex = 5, columnIndex = 1),
                Coordinates(rowIndex = 5, columnIndex = 5),
                Coordinates(rowIndex = 3, columnIndex = 1),
                Coordinates(rowIndex = 3, columnIndex = 5),
                Coordinates(rowIndex = 2, columnIndex = 2),
                Coordinates(rowIndex = 2, columnIndex = 4),
            )
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][ ][ ][X][ ][ ][ ][ ]
     *  6 [ ][N][ ][ ][ ][ ][ ][ ]
     *  5 [ ][ ][ ][X][ ][ ][ ][ ]
     *  4 [X][ ][X][ ][ ][ ][ ][ ]
     *  3 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  2 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  1 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  0 [ ][ ][ ][ ][ ][ ][ ][ ]
     */

    @Test
    fun test_6_1() {
        val knight = Knight(coordinates = Coordinates(rowIndex = 6, columnIndex = 1), boardSize = 8)
        assertThat(knight.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 7, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 3),
                Coordinates(rowIndex = 4, columnIndex = 0),
                Coordinates(rowIndex = 4, columnIndex = 2),
            )
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][N][ ][ ][ ][ ][ ][ ]
     *  6 [ ][ ][ ][X][ ][ ][ ][ ]
     *  5 [X][ ][X][ ][ ][ ][ ][ ]
     *  4 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  3 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  2 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  1 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  0 [ ][ ][ ][ ][ ][ ][ ][ ]
     */

    @Test
    fun test_7_1() {
        val knight = Knight(coordinates = Coordinates(rowIndex = 7, columnIndex = 1), boardSize = 8)
        assertThat(knight.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 6, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 0),
                Coordinates(rowIndex = 5, columnIndex = 2),
            )
        )
    }
}