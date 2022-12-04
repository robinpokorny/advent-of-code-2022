private typealias Assignment = Pair<IntRange, IntRange>

private fun parse(input: List<String>): List<Assignment> = input
    .map(fun(line): List<IntRange> {
        return line
            .split(",")
            .map { it.split("-").map(String::toInt) }
            .map { (first, second) -> first..second }
    })
    .map { (first, second) -> first to second }

private fun countFullyContaining(assignments: List<Assignment>): Int = assignments
    .count { (left, right) ->
        left.minus(right).isEmpty() or right.minus(left).isEmpty()
    }

private fun countOverlapping(assignments: List<Assignment>): Int = assignments
    .count { (left, right) -> left.intersect(right).isNotEmpty() }

fun main() {
    val input = parse(readDayInput(4))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(countFullyContaining(testInput), 2)
    println("Part1: ${countFullyContaining(input)}")

    // PART 2
    assertEquals(countOverlapping(testInput), 4)
    println("Part2: ${countOverlapping(input)}")
}

private val rawTestInput = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
""".trimIndent().lines()