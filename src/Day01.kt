typealias Backpack = List<Int>

private fun parse(input: String): List<Backpack> = input
    .split("\n\n")
    .map { it.lines().map(String::toInt) }

private fun part1(input: List<Backpack>): Int = input
    .maxOf { it.sum() }

private fun part2(input: List<Backpack>): Int = input
    .map { it.sum() }
    .sorted()
    .takeLast(3)
    .sum()

fun main() {
    val input = parse(readDayInput(1).joinToString("\n"))

    val testInput = parse(testInput)

    assertEquals(part1(testInput), 24000)
    println("Part1: ${part1(input)}")

    assertEquals(part2(testInput), 45000)
    println("Part2: ${part2(input)}")
}

private val testInput = """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
""".trimIndent()