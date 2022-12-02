enum class Outcome {
    WIN, LOSE, DRAW
}

private fun scoreOutcome(outcome: Outcome) = when (outcome) {
    Outcome.WIN -> 6
    Outcome.DRAW -> 3
    Outcome.LOSE -> 0
}

private fun toOutcome(input: String) = when (input) {
    "X" -> Outcome.LOSE
    "Y" -> Outcome.DRAW
    "Z" -> Outcome.WIN
    else -> throw Exception("Unsupported outcome")
}

enum class Move {
    ROCK, PAPER, SCISSORS
}

private fun scoreMove(mine: Move) = when (mine) {
    Move.ROCK -> 1
    Move.PAPER -> 2
    Move.SCISSORS -> 3
}

private fun toMove(input: String) = when (input) {
    "A", "X" -> Move.ROCK
    "B", "Y" -> Move.PAPER
    "C", "Z" -> Move.SCISSORS
    else -> throw Exception("Unsupported move")
}

private fun parse(input: List<String>) = input
    .map { it.split(" ") }
    .map { it[0] to it[1] }

private val winningRounds = mapOf(
    Move.ROCK to Move.PAPER,
    Move.PAPER to Move.SCISSORS,
    Move.SCISSORS to Move.ROCK
)

private val losingRounds = winningRounds.entries.associate { (k, v) -> v to k }

private fun scoreRound1(round: Pair<Move, Move>): Int {
    val (theirs, mine) = round

    val outcome =
        if (theirs == mine) Outcome.DRAW
        else if (winningRounds[theirs] == mine) Outcome.WIN
        else Outcome.LOSE

    return scoreMove(mine) + scoreOutcome(outcome)
}

private fun calcTotalScore1(rounds: List<Pair<String, String>>): Int = rounds
    .map { (first, second) -> toMove(first) to toMove(second) }
    .map(::scoreRound1)
    .sum()


private fun scoreRound2(round: Pair<Move, Outcome>): Int {
    val (theirs, outcome) = round

    val mine: Move = when (outcome) {
        Outcome.DRAW -> theirs
        Outcome.WIN -> winningRounds[theirs]!!
        Outcome.LOSE -> losingRounds[theirs]!!
    }

    return scoreMove(mine) + scoreOutcome(outcome)
}

private fun calcTotalScore2(rounds: List<Pair<String, String>>): Int = rounds
    .map { (first, second) -> toMove(first) to toOutcome(second) }
    .map(::scoreRound2)
    .sum()

fun main() {
    val input = parse(readDayInput(2))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(calcTotalScore1(testInput), 15)
    println("Part1: ${calcTotalScore1(input)}")

    // PART 2
    assertEquals(calcTotalScore2(testInput), 12)
    println("Part2: ${calcTotalScore2(input)}")
}

private val rawTestInput = """
A Y
B X
C Z
""".trimIndent().lines()