# Advent of Code 2022 🎄

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/robinpokorny/advent-of-code-2022?style=for-the-badge)

> _Advent of Code 2022 solutions by **Robin Pokorny**:
[Home page](https://robinpokorny.com/)
|
[Twitter](https://twitter.com/robinpokorny)
|
[YouTube](https://www.youtube.com/c/robinpokorny)_

### Organisation
For each puzzle, there is a file `src/DayXX.kt`, where `XX` is a zero-leading number of the puzzle.

As each puzzle has two levels, inside the puzzle's file, there are two functions, `part1` and `part2`. They contain the solution for first and second levels. They might use common methods, and there might be common input parsing. The second part of the puzzle is available only upon completion of the first part. The solution of the first part might be refactored after that; check the file history to see the original solution of the first part.

Traditionally, each puzzle has two inputs. One simple for testing and second more complex for the actual puzzle. The test input is embedded at the end of the file, the production input is expected in a file called `src/DayXX.txt` (not published, see below). The input is the same for both parts of the puzzle.

The `main` function stitches these components together. It reads the inputs, **optionally parses/sanitises/transforms them**, calls both parts on both inputs, assesses that the test input produces the correct result, and prints the results for the production input.

> **Note**
> The inputs (`.txt` files) for the puzzles are not published in this repo as [requested by the AoC author](https://twitter.com/ericwastl/status/1465805354214830081).

There are utilities in the `scr/Utils.kt` folder. If you see an unknown function, please check this file. It should contain tools useful for several solutions.

### About AoC
[Advent of Code](http://adventofcode.com) (AoC) is an online programming event. Each year, starting on `-12-01`, an advent calendar of small programming puzzles are unlocked once a day at midnight (EST/UTC-5). Developers of all skill sets are encouraged to solve them in any programming language they choose!

### Past years
* 2020 in TypeScript:  [GitHub](https://github.com/robinpokorny/advent-of-code-2020)
