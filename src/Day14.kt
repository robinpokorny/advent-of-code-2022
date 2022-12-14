import kotlin.math.max
import kotlin.math.min

private fun line(from: Point, to: Point): List<Point> =
    if (from.x == to.x)
        (min(from.y, to.y)..max(from.y, to.y)).map { Point(to.x, it) }
    else if (from.y == to.y)
        (min(from.x, to.x)..max(from.x, to.x)).map { Point(it, to.y) }
    else
        error("No support for diagonals.")


private fun parse(input: List<String>) = input.flatMap { line ->
    line
        .split(" -> ")
        .map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }
        .windowed(2)
        .flatMap { (from, to) -> line(from, to) }
}

private tailrec fun moveGrain(
    from: Point,
    isFinished: (Point) -> Boolean,
    taken: HashSet<Point>,
    floor: Int = Int.MAX_VALUE
): Boolean {
    if (isFinished(from)) return true

    listOf(0, -1, 1)
        .map { Point(from.x + it, from.y + 1) }
        .find { !taken.contains(it) && it.y < floor }
        ?.let { return moveGrain(it, isFinished, taken, floor) }

    taken.add(from)
    return false
}

private fun part1(cave: List<Point>): Int {
    val taken = cave.toHashSet()
    val source = Point(500, 0)
    val voidStart = cave.maxOf { it.y }
    var sandGrains = -1
    var finished = false

    while (!finished) {
        sandGrains++
        finished = moveGrain(source, { it.y >= voidStart }, taken)
    }

    return sandGrains
}

private fun part2(cave: List<Point>): Int {
    val taken = cave.toHashSet()
    val source = Point(500, 0)
    val floor = cave.maxOf { it.y } + 2
    var sandGrains = -1
    var finished = false

    while (!finished) {
        sandGrains++
        finished = moveGrain(source, { _ -> taken.contains(source) }, taken, floor)
    }

    return sandGrains
}

fun main() {
    val input = parse(readDayInput(14))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 24)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 93)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent().lines()