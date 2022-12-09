import kotlin.math.sign

private fun parse(input: List<String>): List<Char> = input
    .flatMap {
        val (dir, steps) = it.split(" ")
        List(steps.toInt()) { _ -> dir.single() }
    }

data class Point(val x: Int, val y: Int)

private fun moveHead(head: Point, move: Char): Point = when (move) {
    'R' -> head.copy(x = head.x + 1)
    'L' -> head.copy(x = head.x - 1)
    'U' -> head.copy(y = head.y - 1)
    'D' -> head.copy(y = head.y + 1)
    else -> head // Never
}

private fun moveTail(tail: Point, head: Point): Point {
    val dx = head.x - tail.x
    val dy = head.y - tail.y

    val touching = dx in -1..1 && dy in -1..1

    if (touching) return tail

    return Point(tail.x + dx.sign, tail.y + dy.sign)
}

private fun countTailPositions(motions: List<Char>): Int = motions
    .scan(Point(0, 0), ::moveHead)
    .scan(Point(0, 0), ::moveTail)
    .toSet()
    .size

private fun count10TailPositions(motions: List<Char>): Int = motions
    .scan(Point(0, 0), ::moveHead)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .scan(Point(0, 0), ::moveTail)
    .toSet()
    .size

fun main() {
    val input = parse(readDayInput(9))
    val testInput = parse(rawTestInput)
    val testInput2 = parse(rawTestInput2)

    // PART 1
    assertEquals(countTailPositions(testInput), 13)
    println("Part1: ${countTailPositions(input)}")

    // PART 2
    assertEquals(count10TailPositions(testInput2), 36)
    println("Part2: ${count10TailPositions(input)}")
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

private val rawTestInput2 = """
    R 5
    U 8
    L 8
    D 3
    R 17
    D 10
    L 25
    U 20
""".trimIndent().lines()