private fun parse(input: List<String>) = input
    .flatMap {
        if (it == "noop")
            listOf(0)
        else {
            val diff = it.substring(5).toInt()
            listOf(0, diff)
        }
    }

private fun countEvery40Cycle(instructions: List<Int>): Int = instructions
    .let { listOf(0).plus(it) } // Can use `i` instead of `i + 1` below
    .foldIndexed(1 to 0) { i, (x, sum), diff ->
        if (i % 40 == 20)
            Pair(x + diff, sum + i * x)
        else
            Pair(x + diff, sum)
    }
    .second

private fun drawDisplay(instructions: List<Int>): List<String> = instructions
    .scanIndexed(1 to ' ') { i, (x, _), diff -> Pair(
        x + diff,
        if (i % 40 in (x - 1)..(x + 1)) '#' else '.'
    )}
    .drop(1) // Initial value
    .map { it.second }
    .chunked(40) // To lines
    .map { it.joinToString("") }


fun main() {
    val input = parse(readDayInput(10))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(countEvery40Cycle(testInput), 13140)
    println("Part1: ${countEvery40Cycle(input)}")

    // PART 2
    assertEquals(drawDisplay(testInput), expectedCrt)
    println("Part2:")
    drawDisplay(input).forEach { println(it) }
}

private val expectedCrt = """
    ##..##..##..##..##..##..##..##..##..##..
    ###...###...###...###...###...###...###.
    ####....####....####....####....####....
    #####.....#####.....#####.....#####.....
    ######......######......######......####
    #######.......#######.......#######.....
""".trimIndent().lines()

private val rawTestInput = """
    addx 15
    addx -11
    addx 6
    addx -3
    addx 5
    addx -1
    addx -8
    addx 13
    addx 4
    noop
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx -35
    addx 1
    addx 24
    addx -19
    addx 1
    addx 16
    addx -11
    noop
    noop
    addx 21
    addx -15
    noop
    noop
    addx -3
    addx 9
    addx 1
    addx -3
    addx 8
    addx 1
    addx 5
    noop
    noop
    noop
    noop
    noop
    addx -36
    noop
    addx 1
    addx 7
    noop
    noop
    noop
    addx 2
    addx 6
    noop
    noop
    noop
    noop
    noop
    addx 1
    noop
    noop
    addx 7
    addx 1
    noop
    addx -13
    addx 13
    addx 7
    noop
    addx 1
    addx -33
    noop
    noop
    noop
    addx 2
    noop
    noop
    noop
    addx 8
    noop
    addx -1
    addx 2
    addx 1
    noop
    addx 17
    addx -9
    addx 1
    addx 1
    addx -3
    addx 11
    noop
    noop
    addx 1
    noop
    addx 1
    noop
    noop
    addx -13
    addx -19
    addx 1
    addx 3
    addx 26
    addx -30
    addx 12
    addx -1
    addx 3
    addx 1
    noop
    noop
    noop
    addx -9
    addx 18
    addx 1
    addx 2
    noop
    noop
    addx 9
    noop
    noop
    noop
    addx -1
    addx 2
    addx -37
    addx 1
    addx 3
    noop
    addx 15
    addx -21
    addx 22
    addx -6
    addx 1
    noop
    addx 2
    addx 1
    noop
    addx -10
    noop
    noop
    addx 20
    addx 1
    addx 2
    addx 2
    addx -6
    addx -11
    noop
    noop
    noop
""".trimIndent().lines()