private fun parse(input: List<String>) = input

private fun part1(input: List<String>): Int {
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(0))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 0)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 0)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
""".trimIndent().lines()