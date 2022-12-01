import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readDayInput(day: Int): File {
    val fileName = day.toString().padStart(2, '0')
    return File("src", "Day$fileName.txt")
}

/**
 * Compares the two inputs and throws if they are not equal
 */
fun <T> assertEquals(actual: T, expected: T) {
    check(actual == expected) { "Assert failed: expected `$expected`, received `$actual`" }
}