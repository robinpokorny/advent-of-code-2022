data class Directory(
    val name: String,
    val parent: Directory?,
    var size: Int? = null,
    val dirs: MutableList<Directory> = mutableListOf(),
    val files: MutableMap<String, Int> = mutableMapOf()
)

sealed class TerminalOutput {
    class CD(val path: String) : TerminalOutput()

    class File(val name: String, val size: Int) : TerminalOutput()
}

private fun parseToOutput(input: List<String>): List<TerminalOutput> = input
    .mapNotNull {
        if (it[0] == '$')
            if (it[2] == 'c')
                TerminalOutput.CD(it.substring(5))
            else
                null
        else
            if (it[0] == 'd')
                null
            else
                it.split(' ')
                    .let { (size, name) ->
                        TerminalOutput.File(name, size.toInt())
                    }
    }

private fun countSizes(root: Directory): Unit {
    if (root.size != null) return

    root.dirs.forEach(::countSizes)

    root.size = root.files.values.sum() +
            root.dirs.sumOf { it.size ?: 0 }
}

private fun parseToTree(input: List<String>): Directory {
    val output = parseToOutput(input)

    val root = Directory("/", null)

    output.fold(root) { dir, line ->
        when (line) {
            is TerminalOutput.CD -> when (line.path) {
                ".." -> dir.parent!!
                "/" -> root
                else -> Directory(line.path, dir)
                    .also { dir.dirs.add(it) }
            }

            is TerminalOutput.File -> dir.also {
                it.files.set(line.name, line.size)
            }
        }
    }

    countSizes(root)

    return root
}

private fun sumDirsUnder100k(root: Directory): Int {
    val x = root.size?.takeIf { it < 100_000 } ?: 0

    return x + root.dirs.sumOf(::sumDirsUnder100k)
}

private fun findDirsLargerThan(root: Directory, limit: Int): List<Directory> {
    if (root.size!! < limit) return listOf()

    return root.dirs.flatMap { findDirsLargerThan(it, limit) }.plus(root)
}

private fun smallestToDelete(root: Directory): Int =
    findDirsLargerThan(root, root.size!! - 40000000).minOf { it.size ?: 0 }


fun main() {
    val input = parseToTree(readDayInput(7))
    val testInput = parseToTree(rawTestInput)

    // PART 1
    assertEquals(sumDirsUnder100k(testInput), 95437)
    println("Part1: ${sumDirsUnder100k(input)}")

    // PART 2
    assertEquals(smallestToDelete(testInput), 24933642)
    println("Part2: ${smallestToDelete(input)}")
}

private val rawTestInput = """
    ${'$'} cd /
    ${'$'} ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    ${'$'} cd a
    ${'$'} ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    ${'$'} cd e
    ${'$'} ls
    584 i
    ${'$'} cd ..
    ${'$'} cd ..
    ${'$'} cd d
    ${'$'} ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
""".trimIndent().lines()