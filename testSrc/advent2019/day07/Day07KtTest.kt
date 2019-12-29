package advent2019.day07

import advent2019.bi
import advent2019.readAllLines
import org.junit.jupiter.api.Test
import kotlin.test.expect

internal class Day07KtTest {

    @Test
    fun sample139629729() {
        expect(
            139629729.toBigInteger(), {
                run2(
                    "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5",
                    listOf(9, 8, 7, 6, 5).map { i -> i.toBigInteger() }
                )
            }
        )
    }

    @Test
    fun puzzlePart1() {
        val input = readAllLines("data/input-2019-07.txt").single()
        expect(262086.bi) { part1(input) }
    }

    @Test
    fun puzzlePart2() {
        val input = readAllLines("data/input-2019-07.txt").single()
        expect(5371621.bi) { part2(input) }
    }


}
