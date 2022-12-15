import kotlin.math.*
import kotlin.system.measureTimeMillis

private data class Sensor(
    val position: Point,
    val minBeacon: Point,
    val distance: Int
)

private val lineRe =
    Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

private fun parse(input: List<String>): List<Sensor> = input
    .map {
        val (sx, sy, bx, by) = lineRe
            .matchEntire(it)!!
            .destructured
            .toList()
            .map { it.toInt() }

        Sensor(
            Point(sx, sy),
            Point(bx, by),
            abs(sx - bx) + abs(sy - by)
        )
    }

private fun seenOnRow(sensors: List<Sensor>, row: Int) = sensors
    .mapNotNull { (position, _, distance) ->
        val side = distance - abs(position.y - row)
        if (side > 0)
            (position.x - side).rangeTo(position.x + side)
        else
            null
    }

private fun part1(sensors: List<Sensor>, row: Int): Int {
    val seen = seenOnRow(sensors, row)

    // For some reason this is quite fast
    val visibleOnRow = seen.fold(mutableSetOf<Int>()) { prev, next ->
        prev.addAll(next)
        prev
    }.size

    val beaconsOnRow = sensors
        .map { it.minBeacon }
        .distinct()
        .count { it.y == row }

    return visibleOnRow - beaconsOnRow
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(15))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput, 10), 26)
    println("Part1: ${part1(input, 2_000_000)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Sensor at x=2, y=18: closest beacon is at x=-2, y=15
    Sensor at x=9, y=16: closest beacon is at x=10, y=16
    Sensor at x=13, y=2: closest beacon is at x=15, y=3
    Sensor at x=12, y=14: closest beacon is at x=10, y=16
    Sensor at x=10, y=20: closest beacon is at x=10, y=16
    Sensor at x=14, y=17: closest beacon is at x=10, y=16
    Sensor at x=8, y=7: closest beacon is at x=2, y=10
    Sensor at x=2, y=0: closest beacon is at x=2, y=10
    Sensor at x=0, y=11: closest beacon is at x=2, y=10
    Sensor at x=20, y=14: closest beacon is at x=25, y=17
    Sensor at x=17, y=20: closest beacon is at x=21, y=22
    Sensor at x=16, y=7: closest beacon is at x=15, y=3
    Sensor at x=14, y=3: closest beacon is at x=15, y=3
    Sensor at x=20, y=1: closest beacon is at x=15, y=3
""".trimIndent().lines()