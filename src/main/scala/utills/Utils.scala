package utills

object Utils {
  def measureTime[A](calc: => A, msg: String) {
    println(msg)

    val t1 = System.nanoTime
    val result = calc
    val t2 = System.nanoTime

    val ms = (t2 - t1) / 1000000

    println(
      s"\tResult: \t\t$result\n\tElapsed time: \t$ms millis"
    )
  }
}
