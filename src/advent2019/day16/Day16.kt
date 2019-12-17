package advent2019.day16

import advent2019.lcm
import advent2019.logWithTime
import advent2019.readAllLines

fun main() {
    val input = readAllLines("input-2019-16.txt").single()
        .also { logWithTime("input length: ${it.length}") }
    fft(input, 100).take(8)
        .also { logWithTime("part 1: $it") }

    fftRepeat(input, 10000, 100, 7, 8)
        .also { logWithTime("part 2: $it") }

}

interface DigitsProvider {
    // 1-based
    operator fun get(i: Int): Int

    val cycle: Int
    val repeats: Int get() = 1
    val length: Int get() = cycle * repeats

    fun asString(): String {
        return buildString {
            repeat(this@DigitsProvider.length.coerceAtMost(100))
            { append(this@DigitsProvider[it + 1]) }
        }
    }

}

class StringDigitsProvider(val input: String, override val repeats: Int = 1) : DigitsProvider {
    override fun get(i: Int) = input[(i - 1) % cycle] - '0'
    override val cycle = input.length
}

class CachingDigitsProvider(
    val input: DigitsProvider,
    override val repeats: Int = 1, val op: (Int) -> Int
) : DigitsProvider {
    private val cache = (1..input.length).map { op(it) }
    override fun get(i: Int) = cache[(i - 1) % cycle]

    override val cycle = input.length
}

fun fftSingle(input: DigitsProvider, i: Int): Int {
    var sum = 0
    val lcm = lcm(input.cycle, 4 * i)
    val noOfCycles = input.length / lcm
    if (noOfCycles > 0) {
        (1..lcm).forEach {
            if ((it / i) % 4 == 1) sum += noOfCycles * input[it]
            if ((it / i) % 4 == 3) sum -= noOfCycles * input[it]
        }
    }
    (1..input.length % lcm).forEach {
        if ((it / i) % 4 == 1) sum += input[it]
        if ((it / i) % 4 == 3) sum -= input[it]
    }

    return when {
        sum > 0 -> sum % 10
        sum < 0 -> (-sum) % 10
        else -> 0
    }
}

fun fft(input: DigitsProvider, iterations: Int): DigitsProvider {
    return (1..iterations).fold(input) { provider, iter ->
        fftPhase(provider)
            .also { logWithTime("$iter... ${it.asString()}") }
    }
}

fun fftPhase(input: DigitsProvider): DigitsProvider {
    return CachingDigitsProvider(input) { fftSingle(input, it) }
}

fun fftRepeat(input: String, repeats: Int, iterations: Int, offset: Int, messageLen: Int): String {
    return fft(
        StringDigitsProvider(input, repeats),
        iterations
    )
        .let { provider -> buildString { repeat(messageLen) { append(provider[it + 1 + offset]) } } }
}

fun fft(input: String, iterations: Int): String {
    return fft(StringDigitsProvider(input), iterations)
        .let { provider -> buildString { repeat(provider.length) { append(provider[it + 1]) } } }
}