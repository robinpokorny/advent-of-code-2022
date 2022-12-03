typealias Rucksack = CharArray

private fun parse(input: List<String>): List<Rucksack> = input
    .map { it.toCharArray() }


private fun findDuplicate(rucksack: Rucksack): Char {
    val size = rucksack.size

    val left = rucksack.copyOfRange(0, (size + 1) / 2).toSet()
    val right = rucksack.copyOfRange((size + 1) / 2, size).toSet()

    return left.intersect(right).first()
}

private fun itemToPriority(item: Char): Int =
    if (item.code > 90) item.code - 96 else item.code - 64 + 26

private fun sumPrioOfDuplicates(rucksacks: List<Rucksack>) = rucksacks
    .map(::findDuplicate)
    .map(::itemToPriority)
    .sum()

fun main() {
    val input = parse(readDayInput(3))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(sumPrioOfDuplicates(testInput), 157)
    println("Part1: ${sumPrioOfDuplicates(input)}")
}

private val rawTestInput = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent().lines()