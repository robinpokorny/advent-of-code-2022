class Tree(val value: Int, val i: Int, val j: Int)

typealias Forrest = List<List<Tree>>

private fun parse(input: List<String>): Forrest = input.mapIndexed { i, line ->
    line.toList().mapIndexed { j, char -> Tree(char.digitToInt(), i, j) }
}

private fun findIn1D(seen: MutableSet<Tree>) =
    { highest: Int, tree: Tree ->
        if (tree.value > highest) {
            seen.add(tree)
            tree.value
        } else
            highest
    }

private fun findVisible(forest: Forrest): Int {
    val seen = mutableSetOf<Tree>()

    forest.forEach {
        it.fold(-1, findIn1D(seen))
        it.foldRight(-1) { tree, highest -> findIn1D(seen)(highest, tree) }
    }

    forest[0].forEachIndexed { i, _ ->
        val column = forest.map { it[i] }
        column.fold(-1, findIn1D(seen))
        column.foldRight(-1) { tree, highest -> findIn1D(seen)(highest, tree) }
    }

    return seen.size
}


private fun findHighestScenic(forest: Forrest): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(8))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(findVisible(testInput), 21)
    println("Part1: ${findVisible(input)}")

    // PART 2
    // assertEquals(findHighestScenic(testInput), 0)
    // println("Part2: ${findHighestScenic(input)}")
}

private val rawTestInput = """
    30373
    25512
    65332
    33549
    35390
""".trimIndent().lines()