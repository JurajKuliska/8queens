package com.jurajkuliska.eightqueens.game.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test


/**    0  1  2  3  4  5  6  7
 *  7 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  6 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  5 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  4 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  3 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  2 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  1 [ ][ ][ ][ ][ ][ ][ ][ ]
 *  0 [ ][ ][ ][ ][ ][ ][ ][ ]
 */
class QueenTest {

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][ ][x][ ][x][ ][x][ ]
     *  6 [ ][ ][ ][x][x][x][ ][ ]
     *  5 [x][x][x][x][Q][x][x][x]
     *  4 [ ][ ][ ][x][x][x][ ][ ]
     *  3 [ ][ ][x][ ][x][ ][x][ ]
     *  2 [ ][x][ ][ ][x][ ][ ][x]
     *  1 [x][ ][ ][ ][x][ ][ ][ ]
     *  0 [ ][ ][ ][ ][x][ ][ ][ ]
     */
    @Test
    fun test_Queen_2_4() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 5, columnIndex = 4), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 5, columnIndex = 0),
                Coordinates(rowIndex = 5, columnIndex = 1),
                Coordinates(rowIndex = 5, columnIndex = 2),
                Coordinates(rowIndex = 5, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 5),
                Coordinates(rowIndex = 5, columnIndex = 6),
                Coordinates(rowIndex = 5, columnIndex = 7),

                Coordinates(rowIndex = 0, columnIndex = 4),
                Coordinates(rowIndex = 1, columnIndex = 4),
                Coordinates(rowIndex = 2, columnIndex = 4),
                Coordinates(rowIndex = 3, columnIndex = 4),
                Coordinates(rowIndex = 4, columnIndex = 4),
                Coordinates(rowIndex = 6, columnIndex = 4),
                Coordinates(rowIndex = 7, columnIndex = 4),

                Coordinates(rowIndex = 6, columnIndex = 3),
                Coordinates(rowIndex = 7, columnIndex = 2),

                Coordinates(rowIndex = 4, columnIndex = 3),
                Coordinates(rowIndex = 3, columnIndex = 2),
                Coordinates(rowIndex = 2, columnIndex = 1),
                Coordinates(rowIndex = 1, columnIndex = 0),

                Coordinates(rowIndex = 6, columnIndex = 5),
                Coordinates(rowIndex = 7, columnIndex = 6),

                Coordinates(rowIndex = 4, columnIndex = 5),
                Coordinates(rowIndex = 3, columnIndex = 6),
                Coordinates(rowIndex = 2, columnIndex = 7),
            ),
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][ ][ ][X][ ][ ][ ][ ]
     *  6 [ ][ ][ ][X][ ][ ][ ][ ]
     *  5 [ ][ ][ ][X][ ][ ][ ][ ]
     *  4 [ ][ ][ ][X][ ][ ][ ][X]
     *  3 [x][ ][ ][X][ ][ ][X][ ]
     *  2 [ ][X][ ][X][ ][X][ ][ ]
     *  1 [ ][ ][X][X][X][ ][ ][ ]
     *  0 [X][X][X][Q][X][X][X][X]
     */
    @Test
    fun test_Queen_0_3() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 0, columnIndex = 3), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 0, columnIndex = 0),
                Coordinates(rowIndex = 0, columnIndex = 1),
                Coordinates(rowIndex = 0, columnIndex = 2),
                Coordinates(rowIndex = 0, columnIndex = 4),
                Coordinates(rowIndex = 0, columnIndex = 5),
                Coordinates(rowIndex = 0, columnIndex = 6),
                Coordinates(rowIndex = 0, columnIndex = 7),

                Coordinates(rowIndex = 1, columnIndex = 3),
                Coordinates(rowIndex = 2, columnIndex = 3),
                Coordinates(rowIndex = 3, columnIndex = 3),
                Coordinates(rowIndex = 4, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 3),
                Coordinates(rowIndex = 6, columnIndex = 3),
                Coordinates(rowIndex = 7, columnIndex = 3),

                Coordinates(rowIndex = 1, columnIndex = 2),
                Coordinates(rowIndex = 2, columnIndex = 1),
                Coordinates(rowIndex = 3, columnIndex = 0),

                Coordinates(rowIndex = 1, columnIndex = 4),
                Coordinates(rowIndex = 2, columnIndex = 5),
                Coordinates(rowIndex = 3, columnIndex = 6),
                Coordinates(rowIndex = 4, columnIndex = 7),
            ),
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [x][ ][ ][ ][ ][ ][ ][x]
     *  6 [x][ ][ ][ ][ ][ ][x][ ]
     *  5 [x][ ][ ][ ][ ][x][ ][ ]
     *  4 [x][ ][ ][ ][x][ ][ ][ ]
     *  3 [x][ ][ ][x][ ][ ][ ][ ]
     *  2 [x][ ][x][ ][ ][ ][ ][ ]
     *  1 [x][x][ ][ ][ ][ ][ ][ ]
     *  0 [Q][x][x][x][x][x][x][x]
     */
    @Test
    fun test_Queen_0_0() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 0, columnIndex = 1),
                Coordinates(rowIndex = 0, columnIndex = 2),
                Coordinates(rowIndex = 0, columnIndex = 3),
                Coordinates(rowIndex = 0, columnIndex = 4),
                Coordinates(rowIndex = 0, columnIndex = 5),
                Coordinates(rowIndex = 0, columnIndex = 6),
                Coordinates(rowIndex = 0, columnIndex = 7),

                Coordinates(rowIndex = 1, columnIndex = 0),
                Coordinates(rowIndex = 2, columnIndex = 0),
                Coordinates(rowIndex = 3, columnIndex = 0),
                Coordinates(rowIndex = 4, columnIndex = 0),
                Coordinates(rowIndex = 5, columnIndex = 0),
                Coordinates(rowIndex = 6, columnIndex = 0),
                Coordinates(rowIndex = 7, columnIndex = 0),

                Coordinates(rowIndex = 1, columnIndex = 1),
                Coordinates(rowIndex = 2, columnIndex = 2),
                Coordinates(rowIndex = 3, columnIndex = 3),
                Coordinates(rowIndex = 4, columnIndex = 4),
                Coordinates(rowIndex = 5, columnIndex = 5),
                Coordinates(rowIndex = 6, columnIndex = 6),
                Coordinates(rowIndex = 7, columnIndex = 7),
            ),
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [x][x][x][x][x][x][x][Q]
     *  6 [ ][ ][ ][ ][ ][ ][x][x]
     *  5 [ ][ ][ ][ ][ ][x][ ][x]
     *  4 [ ][ ][ ][ ][x][ ][ ][x]
     *  3 [ ][ ][ ][x][ ][ ][ ][x]
     *  2 [ ][ ][x][ ][ ][ ][ ][x]
     *  1 [ ][x][ ][ ][ ][ ][ ][x]
     *  0 [x][ ][ ][ ][ ][ ][ ][x]
     */
    @Test
    fun test_Queen_7_7() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 7, columnIndex = 7), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 7, columnIndex = 0),
                Coordinates(rowIndex = 7, columnIndex = 1),
                Coordinates(rowIndex = 7, columnIndex = 2),
                Coordinates(rowIndex = 7, columnIndex = 3),
                Coordinates(rowIndex = 7, columnIndex = 4),
                Coordinates(rowIndex = 7, columnIndex = 5),
                Coordinates(rowIndex = 7, columnIndex = 6),

                Coordinates(rowIndex = 0, columnIndex = 7),
                Coordinates(rowIndex = 1, columnIndex = 7),
                Coordinates(rowIndex = 2, columnIndex = 7),
                Coordinates(rowIndex = 3, columnIndex = 7),
                Coordinates(rowIndex = 4, columnIndex = 7),
                Coordinates(rowIndex = 5, columnIndex = 7),
                Coordinates(rowIndex = 6, columnIndex = 7),

                Coordinates(rowIndex = 0, columnIndex = 0),
                Coordinates(rowIndex = 1, columnIndex = 1),
                Coordinates(rowIndex = 2, columnIndex = 2),
                Coordinates(rowIndex = 3, columnIndex = 3),
                Coordinates(rowIndex = 4, columnIndex = 4),
                Coordinates(rowIndex = 5, columnIndex = 5),
                Coordinates(rowIndex = 6, columnIndex = 6),
            ),
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [x][ ][ ][ ][ ][ ][ ][x]
     *  6 [ ][x][ ][ ][ ][ ][ ][x]
     *  5 [ ][ ][x][ ][ ][ ][ ][x]
     *  4 [ ][ ][ ][x][ ][ ][ ][x]
     *  3 [ ][ ][ ][ ][x][ ][ ][x]
     *  2 [ ][ ][ ][ ][ ][x][ ][x]
     *  1 [ ][ ][ ][ ][ ][ ][x][x]
     *  0 [x][x][x][x][x][x][x][Q]
     */
    @Test
    fun test_Queen_0_7() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 0, columnIndex = 7), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 0, columnIndex = 0),
                Coordinates(rowIndex = 0, columnIndex = 1),
                Coordinates(rowIndex = 0, columnIndex = 2),
                Coordinates(rowIndex = 0, columnIndex = 3),
                Coordinates(rowIndex = 0, columnIndex = 4),
                Coordinates(rowIndex = 0, columnIndex = 5),
                Coordinates(rowIndex = 0, columnIndex = 6),

                Coordinates(rowIndex = 1, columnIndex = 7),
                Coordinates(rowIndex = 2, columnIndex = 7),
                Coordinates(rowIndex = 3, columnIndex = 7),
                Coordinates(rowIndex = 4, columnIndex = 7),
                Coordinates(rowIndex = 5, columnIndex = 7),
                Coordinates(rowIndex = 6, columnIndex = 7),
                Coordinates(rowIndex = 7, columnIndex = 7),

                Coordinates(rowIndex = 1, columnIndex = 6),
                Coordinates(rowIndex = 2, columnIndex = 5),
                Coordinates(rowIndex = 3, columnIndex = 4),
                Coordinates(rowIndex = 4, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 2),
                Coordinates(rowIndex = 6, columnIndex = 1),
                Coordinates(rowIndex = 7, columnIndex = 0),
            ),
        )
    }

    /**    0  1  2  3  4  5  6  7
     *  7 [Q][x][x][x][x][x][x][x]
     *  6 [x][x][ ][ ][ ][ ][ ][ ]
     *  5 [x][ ][x][ ][ ][ ][ ][ ]
     *  4 [x][ ][ ][x][ ][ ][ ][ ]
     *  3 [x][ ][ ][ ][x][ ][ ][ ]
     *  2 [x][ ][ ][ ][ ][x][ ][ ]
     *  1 [x][ ][ ][ ][ ][ ][x][ ]
     *  0 [x][ ][ ][ ][ ][ ][ ][x]
     */
    @Test
    fun test_Queen_7_0() {
        val queen = Queen(coordinates = Coordinates(rowIndex = 7, columnIndex = 0), boardSize = 8)
        assertThat(queen.attacking).isEqualTo(
            setOf(
                Coordinates(rowIndex = 7, columnIndex = 1),
                Coordinates(rowIndex = 7, columnIndex = 2),
                Coordinates(rowIndex = 7, columnIndex = 3),
                Coordinates(rowIndex = 7, columnIndex = 4),
                Coordinates(rowIndex = 7, columnIndex = 5),
                Coordinates(rowIndex = 7, columnIndex = 6),
                Coordinates(rowIndex = 7, columnIndex = 7),

                Coordinates(rowIndex = 0, columnIndex = 0),
                Coordinates(rowIndex = 1, columnIndex = 0),
                Coordinates(rowIndex = 2, columnIndex = 0),
                Coordinates(rowIndex = 3, columnIndex = 0),
                Coordinates(rowIndex = 4, columnIndex = 0),
                Coordinates(rowIndex = 5, columnIndex = 0),
                Coordinates(rowIndex = 6, columnIndex = 0),

                Coordinates(rowIndex = 0, columnIndex = 7),
                Coordinates(rowIndex = 1, columnIndex = 6),
                Coordinates(rowIndex = 2, columnIndex = 5),
                Coordinates(rowIndex = 3, columnIndex = 4),
                Coordinates(rowIndex = 4, columnIndex = 3),
                Coordinates(rowIndex = 5, columnIndex = 2),
                Coordinates(rowIndex = 6, columnIndex = 1),
            ),
        )
    }
}