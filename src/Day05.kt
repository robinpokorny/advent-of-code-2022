typealias Stacks = List<ArrayDeque<Char>>
typealias Steps = List<Triple<Int, Int, Int>>

private fun parse(input: List<String>): Pair<Stacks, Steps> {
    val (rawStacks, rawSteps) = input
        .joinToString("\n")
        .split("\n\n")
        .map { it.lines() }

    val stacksPrepared = rawStacks
        .reversed()
        .map { it.filterIndexed { index, char -> index % 4 == 1  }.toList() }

    val stacks = stacksPrepared
        .first()
        .map { it.digitToInt() - 1 }
        .map { i -> stacksPrepared.mapNotNull { it.getOrNull(i) }.drop(1).filter { it != ' ' } }
        .map { ArrayDeque(it) }

    val re = """.* (\d+) .*(\d+).+(\d+).*""".toRegex()
    val steps = rawSteps
        .map { re.matchEntire(it)!!.groupValues.drop(1).map(String::toInt) }
        .map{ Triple(it[0], it[1] - 1, it[2] - 1) }

    return Pair(stacks, steps)
}

private fun part1(input: Pair<Stacks, Steps>): String {
    val (stacks, steps) = input

    return steps
        .fold(stacks){ s, (count, from, to) ->
            repeat(count) { s[to].addLast(s[from].removeLast()) }

            s
         }
        .map { it.last() }
        .joinToString("")
}

private fun part2(input: Pair<Stacks, Steps>): String {
    val (stacks, steps) = input

    return steps
        .fold(stacks){ s, (count, from, to) ->
            s[to].addAll(s[from].takeLast(count))
            repeat(count) { s[from].removeLast() }

            s
        }
        .map { it.last() }
        .joinToString("")
}

fun main() {
    val input1 = parse(readDayInput(5))
    val testInput1 = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput1), "CMZ")
    println("Part1: ${part1(input1)}")


    // We mutate the stacks so we need a new input
    val input2 = parse(readDayInput(5))
    val testInput2 = parse(rawTestInput)

    // PART 2
    assertEquals(part2(testInput2), "MCD")
    println("Part2: ${part2(input2)}")
}

private val rawTestInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""".trimIndent().lines()