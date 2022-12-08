class Tree(val value: Int, val i: Int, val j: Int) {}

typealias Forrest = List<List<Tree>>

private fun parse(input: List<String>): Forrest = input.mapIndexed { i, line ->
    line.toList().mapIndexed { j, char ->
        Tree(char.digitToInt(), i, j)
    }
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

    forest[0].forEachIndexed { j, _ ->
        val column = forest.map { it[j] }
        column.fold(-1, findIn1D(seen))
        column.foldRight(-1) { tree, highest -> findIn1D(seen)(highest, tree) }
    }

    return seen.size
}

private fun canSeeFrom(tree: Tree, skip: (Tree) -> Boolean) =
    { prev: Pair<Int, Boolean>, current: Tree ->
        if (!prev.second || skip(current)) prev
        else if (current.value >= tree.value) prev.first + 1 to false
        else prev.first + 1 to true
    }

private fun findHighestScenic(forest: Forrest): Int =
    forest.flatten().maxOf { tree ->
        val (right) = forest[tree.i].fold(
            Pair(0, true),
            canSeeFrom(tree) { d -> d.j <= tree.j }
        )
        val (left) = forest[tree.i].foldRight(Pair(0, true)) { c, prev ->
            canSeeFrom(tree) { d -> d.j >= tree.j }(prev, c)
        }

        val column = forest.map { it[tree.j] }

        val (bottom) = column.fold(
            Pair(0, true),
            canSeeFrom(tree) { d -> d.i <= tree.i }
        )
        val (top) = column.foldRight(Pair(0, true)) { c, prev ->
            canSeeFrom(tree) { d -> d.i >= tree.i }(prev, c)
        }

        right * left * bottom * top
    }

fun main() {
    val input = parse(readDayInput(8))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(findVisible(testInput), 21)
    println("Part1: ${findVisible(input)}")

    // PART 2
    assertEquals(findHighestScenic(testInput), 8)
    println("Part2: ${findHighestScenic(input)}")
}

private val rawTestInput = """
    30373
    25512
    65332
    33549
    35390
""".trimIndent().lines()