import java.io.File
import java.util.*

val testInput = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""

fun parseInput(string: String): Map<Int, List<Char>> {
    val matrix = string.split("\n")
        .dropLast(1)
        .map { line ->
            line.windowed(size = 4, step = 4, partialWindows = true)
                .map { s -> s.filter { c -> c.isUpperCase() } }
        }
    return getMapOfColumnsFromMatrix(matrix)
}

fun getMapOfColumnsFromMatrix(matrix: List<List<String>>): Map<Int, List<Char>> {
    val stacks = mutableMapOf<Int, MutableList<Char>>()
    for (row in matrix) {
        for ((colIndex, col) in row.withIndex()) {
            if (col.isBlank()) {
                continue
            }
            val stackIndex = colIndex + 1
            val currentStack = stacks.getOrDefault(stackIndex, mutableListOf())
            currentStack.add(col.first())
            stacks[stackIndex] = currentStack
        }
    }
    return stacks
}

fun parseListsAsStacks(stackMap: Map<Int, List<Char>>): Map<Int, Stack<Char>> {
    return stackMap.entries.associate {
        it.key to it.value.foldRight(Stack<Char>()) { item, acc -> acc.push(item); acc }
    }
}

data class Command(val nrContainers: Int, val from: Int, val to: Int)

fun getCommand(command: String): Command {
    val numbers = command.split(" ").filter { it.any { s -> s.isDigit() } }
    return Command(numbers[0].toInt(), numbers[1].toInt(), numbers[2].toInt())
}

fun performCommand(data: Map<Int, Stack<Char>>, command: Command) {
    repeat(command.nrContainers) { data[command.to]!!.push(data[command.from]!!.pop()) }
}

fun performCommandForTask2(data: Map<Int, Stack<Char>>, command: Command) {
    val values = Stack<Char>()
    repeat(command.nrContainers) { values.push(data[command.from]!!.pop()) }
    while (values.isNotEmpty()) {
        data[command.to]!!.push(values.pop())
    }
}

fun getResult(data: Map<Int, Stack<Char>>): String {
    return data.keys.sorted()
        .map { data[it]!! }
        .map { it.peek() }
        .joinToString(separator = "")
}

fun solveFirst(data: Map<Int, List<Char>>, commands: List<String>): String {
    val dataAsStacks = parseListsAsStacks(data)
    commands.map { getCommand(it) }.forEach { performCommand(dataAsStacks, it) }
    return getResult(dataAsStacks)
}

fun solveSecond(data: Map<Int, List<Char>>, commands: List<String>): String {
    val dataAsStacks = parseListsAsStacks(data)
    commands.map { getCommand(it) }.forEach { performCommandForTask2(dataAsStacks, it) }
    return getResult(dataAsStacks)
}

fun main() {
    val (testData, testCommands) = testInput.split("\n\n")
    println(solveFirst(parseInput(testData), testCommands.split("\n"))) // CMZ

    val input = File("input.txt").readText()
    val (data, commands) = input.split("\n\n")
    println(solveFirst(parseInput(data), commands.trim().split("\n"))) // FWNSHLDNZ

    println(solveSecond(parseInput(testData), testCommands.split("\n"))) // MCD
    println(solveSecond(parseInput(data), commands.trim().split("\n")))  // RNRGDNFQG
}

main()
