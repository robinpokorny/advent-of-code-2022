typealias Backpack = List<Int>

private fun parse(input: String): List<Backpack> = input
    .split("\n\n")
    .map { it.lines().map(String::toInt) }

private fun sumOfLargestBackpack(input: List<Backpack>): Int = input
    .maxOf { it.sum() }

private fun sumOf3LargestBackpacks(input: List<Backpack>): Int = input
    .map { it.sum() }
    .sorted()
    .takeLast(3)
    .sum()

fun main() {
    val input = parse(readDayInput(1).joinToString("\n"))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(sumOfLargestBackpack(testInput), 24000)
    println("Part1: ${sumOfLargestBackpack(input)}")

    // PART 2
    assertEquals(sumOf3LargestBackpacks(testInput), 45000)
    println("Part2: ${sumOf3LargestBackpacks(input)}")
}

private val rawTestInput = """
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