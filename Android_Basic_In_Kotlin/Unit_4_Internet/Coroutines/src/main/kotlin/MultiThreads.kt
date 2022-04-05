fun main() {

    println("Multi-threads")
    val states = arrayOf("Starting", "Doing Task 1", "Doing Task 2", "Ending")

    repeat(3 ){
        Thread {
            println("${Thread.currentThread()} has run")

            for (i in states){
                println("${Thread.currentThread()} - $i")
                Thread.sleep(50)
            }
        }.start()
    }
}