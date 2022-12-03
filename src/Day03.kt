typealias Rucksack = List<Char>

private fun parse(input: List<String>): List<Rucksack> = input
    .map { it.toCharArray().toList() }

private fun itemToPriority(char: Char): Int =
    if (char in 'a'..'z')
        char - 'a' + 1
    else
        char - 'A' + 27

private fun findDuplicate(rucksack: Rucksack): Char = rucksack
    .chunked(rucksack.size / 2)
    .map { it.toSet() }
    .reduce(Set<Char>::intersect)
    .single()

private fun sumPrioOfDuplicates(rucksacks: List<Rucksack>) = rucksacks
    .map(::findDuplicate)
    .sumOf(::itemToPriority)

private fun sumPrioForGroupBadges(input: List<Rucksack>): Int = input
    .map { it.toSet() }
    .chunked(3)
    .map { it.reduce(Set<Char>::intersect).single() }
    .sumOf(::itemToPriority)

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