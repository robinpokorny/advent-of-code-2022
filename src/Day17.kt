class CircularList<T>(private val list: List<T>) {
    private var index = 0

    fun next(): T =
        list[index % list.size]
            .also { index++ }

    fun reset(): Unit {
        index = 0
        return
    }
}

private fun parse(input: List<String>) = input
    .single()
    .toList()
    .mapNotNull {
        when (it) {
            '<' -> Point.LEFT
            '>' -> Point.RIGHT
            else -> null
        }
    }
    .let { CircularList(it) }

private enum class Block(val shape: List<Point>) {
    H(listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0))),
    X(listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(2, 1), Point(1, 2))),
    J(listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2))),
    I(listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))),
    O(listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))),
}

private val blocks =
    CircularList(listOf(Block.H, Block.X, Block.J, Block.I, Block.O))

typealias Grid = MutableList<MutableList<Boolean>>

// Moves the block if possible or returns the original block and if it was moved
private fun moveIfPossible(
    grid: Grid,
    block: List<Point>,
    move: Point
): Pair<List<Point>, Boolean> {
    val candidate = block.map { it + move }

    val free = candidate.all {
        // Check coordinates are valid and there is no other block in the grid
        grid.getOrNull(it.y)?.getOrNull(it.x)?.not() ?: false
    }

    if (free)
        return candidate to true
    else
        return block to false
}

private fun part1(jet: CircularList<Point>): Int {
    val grid = MutableList(10_000) { MutableList(7) { false } }

    blocks.reset()

    generateSequence { blocks.next() }
        .take(2022)
        .forEach { nextBlock ->
            val top = grid.indexOfLast { it.contains(true) }
            val spawn = Point(2, top + 4)
            var block = nextBlock.shape.map { it + spawn }

            while (true) {
                val nextJet = jet.next()
                val (afterJet, _) = moveIfPossible(grid, block, nextJet)

                val (afterDown, movedDown) = moveIfPossible(
                    grid,
                    afterJet,
                    Point.DOWN
                )

                block = afterDown

                if (!movedDown) break
            }

            block.forEach { grid[it.y][it.x] = true }
        }

    return grid.indexOfLast { it.contains(true) } + 1
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(17))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 3068)
    println("Part1: ${part1(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
""".trimIndent().lines()