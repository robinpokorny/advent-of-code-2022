import kotlin.math.max

private fun findMarker(length: Int, datastream: String): Int = datastream
    .windowedSequence(length)
    .indexOfFirst { it.toSet().size == length }
    .plus(length)

fun main() {
    val input = readDayInput(6).single()

    // PART 1
    assertEquals(findMarker(4, "mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7)
    assertEquals(findMarker(4, "bvwbjplbgvbhsrlpgdmjqwftvncz"), 5)
    assertEquals(findMarker(4, "nppdvjthqldpwncqszvftbrmjlhg"), 6)
    assertEquals(findMarker(4, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10)
    assertEquals(findMarker(4, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11)
    println("Part1: ${findMarker(4, input)}")

    // PART 2
    assertEquals(findMarker(14, "mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19)
    assertEquals(findMarker(14, "bvwbjplbgvbhsrlpgdmjqwftvncz"), 23)
    assertEquals(findMarker(14, "nppdvjthqldpwncqszvftbrmjlhg"), 23)
    assertEquals(findMarker(14, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29)
    assertEquals(findMarker(14, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26)
    println("Part1: ${findMarker(14, input)}")
}