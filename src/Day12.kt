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

private fun shortestPath(
    map: Map<Point, Int>,
    start: Point,
    isEnd: (Point) -> Boolean,
    isValidPath: (Point, Point) -> Boolean
): Int {
    val processed = mutableSetOf<Point>()
    val costMap = mutableMapOf<Point, Int>()
    val queue = PriorityQueue<Point> { a, b -> costMap[a]!! - costMap[b]!! }

    // Init
    costMap.set(start, 0)
    queue.add(start)

    while (queue.isNotEmpty()) {
        val next = queue.remove()

        if (processed.contains(next)) continue else processed.add(next)

        val adjacent = listOf(
            next.copy(x = next.x + 1),
            next.copy(x = next.x - 1),
            next.copy(y = next.y - 1),
            next.copy(y = next.y + 1),
        )
            .filter { it in map && isValidPath(next, it) }

        adjacent.forEach {
            costMap.set(it, costMap[next]!! + 1)
            queue.add(it)
        }

        adjacent.find { isEnd(it) }?.also { return costMap[it]!! }
    }

    return 0
}

private fun part1(input: Triple<Map<Point, Int>, Point, Point>): Int {
    val (map, start, end) = input

    return shortestPath(
        map,
        start,
        { it == end },
        { from, to -> map[to]!! - map[from]!! <= 1 })
}

private fun part2(input: Triple<Map<Point, Int>, Point, Point>): Int {
    val (map, _, end) = input

    return shortestPath(
        map,
        end,
        { map[it]!! == 0 },
        { from, to -> map[from]!! - map[to]!! <= 1 })
}

fun main() {
    val input = parse(readDayInput(12))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 31)
    println("Part1: ${part1(input)}")

    // PART 2
    assertEquals(part2(testInput), 29)
    println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent().lines()