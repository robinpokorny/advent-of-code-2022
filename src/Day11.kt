data class Monkey(
    val items: ArrayDeque<Int>,
    val operation: (Int) -> Int,
    val test: Int,
    val onPass: Int,
    val onFail: Int,
    var inspected: Int = 0
)

private fun parse(input: List<String>) = input
    .joinToString("\n")
    .split("\n\n")
    .map { it.lines() }
    .map {
        Monkey(
            it[1]
                .substring(18)
                .split(", ")
                .map(String::toInt)
                .let(::ArrayDeque),
            it[2]
                .substring(23)
                .split(" ")
                .let { (op, arg) ->
                    when {
                        arg == "old" -> { i: Int -> i * i }
                        op == "*" -> { i -> i * (arg.toInt()) }
                        op == "+" -> { i -> i + (arg.toInt()) }
                        else -> error("Unknown operation")
                    }
                },
            it[3].substring(21).toInt(),
            it[4].substring(29).toInt(),
            it[5].substring(30).toInt(),
        )
    }


private fun part1(monkeys: List<Monkey>): Int {
    repeat(20) {
        monkeys.forEach {
            it.inspected += it.items.size

            it.items.forEach { item ->
                val nextItem = it.operation(item) / 3
                val nextMonkey =
                    if (nextItem % it.test == 0) it.onPass else it.onFail

                monkeys[nextMonkey].items.addLast(nextItem)
            }

            it.items.clear()
        }
    }

    return monkeys
        .map { it.inspected }
        .sortedDescending()
        .take(2)
        .let { (a, b) -> a * b }
}

private fun part2(input: List<Monkey>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(11))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 10605)
    println("Part1: ${part1(input)}")

    // PART 2
    // assertEquals(part2(testInput2), 2713310158)
    // println("Part2: ${part2(input2)}")
}

private
val rawTestInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent().lines()