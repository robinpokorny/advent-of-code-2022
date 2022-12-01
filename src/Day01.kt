private fun parse(input: String) = input
    .split(Regex("\n\n"))
    .map { println(it); it }
    .map { it.lines().map(String::toInt) }


private fun part1(input: List<List<Int>>): Int = input
    .map { it.sum() }
    .max()


private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(1).readText())

    val testParsed = parse(testInput)

    assertEquals(part1(testParsed), 24000)
    println("Part1: ${part1(input)}")

    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
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