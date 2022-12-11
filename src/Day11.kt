data class Monkey(
    val items: ArrayDeque<Int>,
    val operation: (Int, Int?) -> Int,
    val test: Int,
    val onPass: Int,
    val onFail: Int,
    var inspected: Long = 0
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
                .let{(op, arg) -> parseOperation(op, arg)},
            it[3].substring(21).toInt(),
            it[4].substring(29).toInt(),
            it[5].substring(30).toInt(),
        )
    }

private fun parseOperation(op: String, arg: String) = when {
    arg == "old" -> { i: Int, mod: Int? ->
        (i.toLong() * i.toLong()).mod(mod ?: Int.MAX_VALUE)
    }
    op == "*" -> { i, mod ->
        (i * (arg.toInt())).mod(mod ?: Int.MAX_VALUE)
    }
    op == "+" -> { i, mod ->
        (i + (arg.toInt())).mod(mod ?: Int.MAX_VALUE)
    }
    else -> error("Unknown operation")
}


private fun part1(monkeys: List<Monkey>): Long {
    repeat(20) {
        monkeys.forEach {
            it.inspected += it.items.size

            it.items.forEach { item ->
                val nextItem = it.operation(item, null) / 3
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

private fun part2(monkeys: List<Monkey>): Long {
    val product = monkeys
        .map { it.test }
        .reduce(Int::times)

    repeat(10_000) {
        monkeys.forEach {
            it.inspected += it.items.size

            it.items.forEach { item ->
                val nextItem = it.operation(item, product)
                val nextMonkey =
                    if (nextItem % it.test == 0) it.onPass else it.onFail

                monkeys[nextMonkey].items.addLast(nextItem)
            }

            it.items.clear()
        }
    }

    monkeys.forEach {
        println(it.inspected)
    }

    return monkeys
        .map { it.inspected }
        .sortedDescending()
        .take(2)
        .let { (a, b) -> a * b }
}

fun main() {
    val input = parse(readDayInput(11))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 10605)
    println("Part1: ${part1(input)}")

    // PART 2
    // We mutate the Monkeys, so we need a new input
    val input2 = parse(readDayInput(11))
    val testInput2 = parse(rawTestInput)

    assertEquals(part2(testInput2), 2713310158)
    println("Part2: ${part2(input2)}")
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