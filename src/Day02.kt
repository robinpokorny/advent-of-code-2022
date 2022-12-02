enum class Move {
    ROCK, PAPER, SCISSORS
}

private fun toMove(input: String) = when (input) {
    "A", "X" -> Move.ROCK
    "B", "Y" -> Move.PAPER
    "C", "Z" -> Move.SCISSORS
    else -> throw Exception("Unsupported move")
}

private fun parse(input: List<String>) = input
    .map { it.split(" ") }
    .map { Pair(toMove(it[0]), toMove(it[1])) }

private val winningRounds = listOf(
    Move.ROCK to Move.PAPER,
    Move.PAPER to Move.SCISSORS,
    Move.SCISSORS to Move.ROCK
)

private fun scoreRound(round: Pair<Move, Move>): Int {
    val (theirs, mine) = round

    val moveScore = when (mine) {
        Move.ROCK -> 1
        Move.PAPER -> 2
        Move.SCISSORS -> 3
    }

    val outcome =
        if (theirs == mine) 3
        else if (round in winningRounds) 6
        else 0

    return moveScore + outcome
}

private fun calcTotalScore(rounds: List<Pair<Move, Move>>): Int = rounds
    .map(::scoreRound)
    .sum()


private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(2))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(calcTotalScore(testInput), 15)
    println("Part1: ${calcTotalScore(input)}")

    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
A Y
B X
C Z
""".trimIndent().lines()