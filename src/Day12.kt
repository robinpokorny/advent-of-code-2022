import java.util.PriorityQueue

private fun parse(input: List<String>): Triple<Map<Point, Int>, Point, Point> {
    var start = Point(0, 0)
    var end = Point(0, 0)

    val map = input
        .flatMapIndexed { i, row ->
            row.mapIndexed { j, item ->
                val point = Point(i, j)
                val value = when (item) {
                    'S' -> 0.also { start = point }
                    'E' -> 25.also { end = point }
                    else -> item - 'a'
                }
                Pair(point, value)
            }
        }
        .toMap()

    return Triple(map, start, end)
}

private fun part1(input: Triple<Map<Point, Int>, Point, Point>): Int {
    val (map, start, end) = input
    val processed = mutableSetOf<Point>()
    val costMap = mutableMapOf<Point, Int>()
    val queue = PriorityQueue<Point> { a, b -> costMap[a]!! - costMap[b]!! }

    // Init
    costMap.set(start, 0)
    queue.add(start)

    while (queue.isNotEmpty()) {
        val next = queue.remove()

        if (processed.contains(next)) continue

        processed.add(next)

        val neighbors = listOf(
            next.copy(x = next.x + 1),
            next.copy(x = next.x - 1),
            next.copy(y = next.y - 1),
            next.copy(y = next.y + 1),
        )
            .filter { it in map && (map[it]!! - map[next]!! <= 1) }
            .forEach {
                costMap.set(it, costMap[next]!! + 1)
                queue.add(it)
            }

        costMap[end]?.also { return it }
    }

    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(12))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 31)
    println("Part1: ${part1(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent().lines()