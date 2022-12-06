import kotlin.math.max

private fun findMarker(datastream: String): Int {
    return datastream
        .splitToSequence("")
        .withIndex()
        .find { (index) -> datastream
            .slice(max(0, index - 3)..index)
            .toSet()
            .size == 4
        }!!
        .index
        .plus(1)
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = readDayInput(6).single()

    // PART 1
    assertEquals(findMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7)
    assertEquals(findMarker("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5)
    assertEquals(findMarker("nppdvjthqldpwncqszvftbrmjlhg"), 6)
    assertEquals(findMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10)
    assertEquals(findMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11)
    println("Part1: ${findMarker(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}