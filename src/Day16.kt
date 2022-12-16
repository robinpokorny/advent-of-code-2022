import kotlin.math.*

private data class Valve(
    val name: String,
    val flow: Int,
    val nearby: List<String>
)

private val lineRe =
    Regex("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)")

private fun parse(input: List<String>): List<Valve> = input
    .map { line ->
        val (name, flow, nearby) = lineRe.matchEntire(line)!!.destructured

        Valve(name, flow.toInt(), nearby.split(", "))
    }

// Distances[i][j] -> from i to j or null
typealias Distances = MutableMap<String, MutableMap<String, Int?>>

// Floyd–Warshall
private fun shortestPaths(distances: Distances) {
    for (k in distances.keys) {
        for (i in distances.keys) {
            for (j in distances.keys) {
                distances[i]!![j] = listOfNotNull(
                    distances[i]!![j],
                    distances[k]!![j]?.let { distances[i]!![k]?.plus(it) }
                ).minOrNull()
            }
        }
    }
}

private fun dfs(
    paths: Distances,
    valves: Map<String, Valve>,
    seen: Set<String>,
    current: String,
    score: Int,
    time: Int
): Int = paths[current]!!
    .maxOf { (valve, dist) ->
        if (!seen.contains(valve) && time + dist!! + 1 < 30) {
            val newScore = score + (30 - time - dist - 1) * valves[valve]!!.flow
            dfs(
                paths,
                valves,
                seen + valve,
                valve,
                newScore,
                time + dist + 1,
            )
        }
        else
            score
    }

private fun part1(valves: List<Valve>): Int {
    val paths: Distances = valves.associate {
        Pair(
            it.name,
            it.nearby
                .associateWith<String, Int?> { 1 }
                .plus(it.name to 0)
                .toMutableMap()
        )
    }.toMutableMap()

    shortestPaths(paths)

    val zeroFlowValveNames = valves.filter { it.flow == 0 }.map { it.name }

    zeroFlowValveNames.forEach { name ->
        paths.values.forEach { it.remove(name) }
    }

    return dfs(
        paths,
        valves.associateBy { it.name }.toMap(),
        emptySet(),
        "AA",
        0,
        0
    )
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val input = parse(readDayInput(16))
    val testInput = parse(rawTestInput)

    // PART 1
    assertEquals(part1(testInput), 1651)
    println("Part1: ${part1(input)}")

    // PART 2
    // assertEquals(part2(testInput), 0)
    // println("Part2: ${part2(input)}")
}

private val rawTestInput = """
    Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
    Valve BB has flow rate=13; tunnels lead to valves CC, AA
    Valve CC has flow rate=2; tunnels lead to valves DD, BB
    Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
    Valve EE has flow rate=3; tunnels lead to valves FF, DD
    Valve FF has flow rate=0; tunnels lead to valves EE, GG
    Valve GG has flow rate=0; tunnels lead to valves FF, HH
    Valve HH has flow rate=22; tunnel leads to valve GG
    Valve II has flow rate=0; tunnels lead to valves AA, JJ
    Valve JJ has flow rate=21; tunnel leads to valve II
""".trimIndent().lines()