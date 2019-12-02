package advent2019

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val sum = Files.readAllLines(Paths.get("day1.txt"))
        .asSequence()
        .map { it.toInt() }
        .map { it to fuel2(it) }
        .onEach { println(it) }
        .map { it.second }
        .sum()

    println(sum)

}

fun fuel(mass: Int): Int {
    return mass / 3 - 2
}

fun fuel2(mass: Int): Int {
    var r = 0
    var f = fuel(mass)
    while (f > 0) {
        r += f
        f = fuel(f)
    }
    return r
}

