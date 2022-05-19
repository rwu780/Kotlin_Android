package Collections

fun main() {

    // Array
    /**
     * There is no mutable version of Array. Once you created an array, you can't add or remove
     * except for copying to a new array
     *
     * Java : Int[] x = int[]{1, 2, 3}
     */
    val x: IntArray = intArrayOf(1, 2, 3)
    println(x.size)


    //List
    /**
     * List
     * Similar to ArrayList in Java
     *
     * Mutable and Immutable
     */

    val mutableList = mutableListOf("Java")
    val mutableList1 = mutableListOf<String>()
    val mutableList2: MutableList<String> = mutableListOf()
    mutableList.add("Python")

    val readOnlyList = listOf("Java")
    readOnlyList.add("Python") // This will give compile error

    // Map
    /**
     * Map
     *
     * mutable vs immutable
     *
     *
     */

    val cures = hashMapOf("white spots" to "Ich", "red sores" to "hole disease")

    // Get element
    println(cures.get("white spots"))
    println(cures["red sores"])


    println(cures.getOrDefault("bloating", "sorry, I don't know"))

    // Mutable Map
    val inventory = mutableMapOf("fish net" to 1)
    inventory.put("tank scrubber", 3)
    println(inventory.toString())
    inventory.remove("fish net")
    println(inventory.toString())

}