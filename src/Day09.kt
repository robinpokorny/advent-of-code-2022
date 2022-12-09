import kotlin.math.sign

typealias Motion = Char

private fun parse(input: List<String>): List<Motion> = input
    .flatMap {
        val (dir, steps) = it.split(" ")
        List(steps.toInt()) { _ -> dir.single() }
    }

data class Point(val x: Int, val y: Int)

private fun countTailPositions(motions: List<Motion>): Int = motions
    .scan(Point(0, 0)) { head, move ->
        when (move) {
            'R' -> head.copy(x = head.x + 1)
            'L' -> head.copy(x = head.x - 1)
            'U' -> head.copy(y = head.y - 1)
            'D' -> head.copy(y = head.y + 1)
            else -> head // Never
        }
    }
    .scan(Point(0, 0)) { tail, head ->
        val dx = head.x - tail.x
        val dy = head.y - tail.y

        val touching = dx in -1..1 && dy in -1..1

        if (touching)
            tail
        else
            Point(tail.x + dx.sign, tail.y + dy.sign)
    }
    .toSet()
    .size

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(9))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(countTailPositions(testInput), 13)
    println("Part1: ${countTailPositions(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
""".trimIndent().lines()