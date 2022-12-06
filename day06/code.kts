import java.io.File

val inputs = listOf(
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb", // 7
    "bvwbjplbgvbhsrlpgdmjqwftvncz", // 5
    "nppdvjthqldpwncqszvftbrmjlhg", // 6
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", // 10
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" // 11
)

fun solve(input: String, windowSize: Int = 4): Int {
    val windowIndex = input.windowed(windowSize)
        .indexOfFirst { it.chars().distinct().count() == windowSize.toLong() }

    return windowIndex + windowSize
}

fun main() {
    println(inputs.map { solve(it) }) // [7, 5, 6, 10, 11]

    val input = File("input.txt").readText()
    println(solve(input)) // 1804

    println(solve("mjqjpqmgbljsphdztnvjfqwrcgsmlb", windowSize = 14)) // 19
    println(solve(input, windowSize = 14)) // 2508
}

main()
