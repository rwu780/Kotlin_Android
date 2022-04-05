fun main() {
    println("Single Thread")

    val thread = Thread {
        println("${Thread.currentThread()} has run")
    }

    thread.start()
}