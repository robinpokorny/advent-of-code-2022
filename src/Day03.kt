typealias Rucksack = CharArray

private fun parse(input: List<String>): List<Rucksack> = input
    .map { it.toCharArray() }


private fun findDuplicate(rucksack: Rucksack): Char {
    val size = rucksack.size

    val left = rucksack.copyOfRange(0, (size + 1) / 2).toSet()
    val right = rucksack.copyOfRange((size + 1) / 2, size).toSet()

    return (left intersect right).single()
}

private fun itemToPriority(char: Char): Int =
    if (char in 'a'..'z')
        char - 'a' + 1
    else
        char - 'A' + 27

private fun sumPrioOfDuplicates(rucksacks: List<Rucksack>) = rucksacks
    .map(::findDuplicate)
    .map(::itemToPriority)
    .sum()

private fun sumPrioForGroupBadges(input: List<Rucksack>): Int = input
    .map { it.toSet() }
    .chunked(3)
    .map { it.reduce(Set<Char>::intersect).single()  }
    .map(::itemToPriority)
    .sum()

fun main() {
    val input = parse(readDayInput(3))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(sumPrioOfDuplicates(testInput), 157)
    println("Part1: ${sumPrioOfDuplicates(input)}")

    // PART 2
    assertEquals(sumPrioForGroupBadges(testInput), 70)
    println("Part2: ${sumPrioForGroupBadges(input)}")
}

private val rawTestInput = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent().lines()