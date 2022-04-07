fun main(args: Array<String>) {

    // lists
    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    println("list: $numbers")
    println("Sorted list: ${numbers.sorted()}")
    println("Reversed Sorted list: ${numbers.sorted().reversed()}")

    println()
    // Sets
    val setOfNumbers = numbers.toSet()
    println("Set: $setOfNumbers")

    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 2, 1)
    println("$set1 == $set2: ${set1 == set2}")
    println("contains 7: ${setOfNumbers.contains(7)}")

    println()
    //maps
    val peopleAges = mutableMapOf<String, Int>(
        "Fred" to 50,
        "Ann" to 23
    )

    println(peopleAges)

    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51

    println(peopleAges)

    peopleAges["Fred"] = 31
    println(peopleAges)

    println("========== Working with Collections ==========")
    peopleAges.forEach { print("${it.key} is ${it.value}, ") }
    println()
    println(peopleAges.map { "${it.key} is ${it.value}" }.toString())

    val age50 = peopleAges.filter { (k, v) -> v >= 50 }
    println(age50)

    println("========== Working with Lambdas ==========")
    val triple: (Int) -> Int = {a: Int -> a * 3}

    val tripleArray = numbers.map { triple(it) }
    println(tripleArray)


    val words = listOf("about", "acute", "awesome", "balloon", "best", "brief", "class", "coffee", "creative")
    val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
    println(filteredWords)

    val filteredWords1 = words.filter { it.startsWith("b", ignoreCase = true) }.shuffled().take(2)
    println(filteredWords1)



}