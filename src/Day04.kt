private fun parse(input: List<String>) = input
    .map(fun(line): List<IntRange> {
        return line
            .split(",")
            .map { it.split("-").map(String::toInt) }
            .map { (first, second) -> first..second }
    })
    .map { (first, second) -> first to second }

private fun countFullyContaining(pairs: List<Pair<IntRange, IntRange>>): Int =
    pairs
        .filter { (left, right) ->
            left.minus(right).isEmpty() or right.minus(left).isEmpty()
        }
        .size

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(4))
    val testInput = parse(rawTestInput)

    println(testInput)

    // PART 1
    assertEquals(countFullyContaining(testInput), 2)
    println("Part1: ${countFullyContaining(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
""".trimIndent().lines()