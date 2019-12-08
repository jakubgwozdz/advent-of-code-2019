package advent2019

fun permutations(n: Int): List<List<Int>> {
    val a: IntArray = (0 until n).toList().toIntArray()
    val result = mutableListOf<List<Int>>()
    generate(n, a) { result.add(it.toList()) }
    return result.toList()
}

fun generate(k: Int, a: IntArray, outputOp: (IntArray)->Unit) {
    if (k == 1) outputOp.invoke(a)
    else {
        generate(k - 1, a, outputOp)
        (0 until k - 1)
            .forEach { i ->
                if (k % 2 == 0)
                    a.swap(i, k - 1)
                else
                    a.swap(0, k - 1)
                generate(k - 1, a, outputOp)
            }
    }
}

fun IntArray.swap(i: Int, j: Int) {
    val v = this[i]
    this[i] = this[j]
    this[j] = v
}