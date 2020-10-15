package utills

object Utils {
  def measureTime[T](calc: => Double, msg: String) {
    println(msg)

    val t1 = System.nanoTime
    val result = calc
    val t2 = System.nanoTime

    println(
      "\tResult: \t\t%s\n\tElapsed time: \t%s millis"
        .format(result, (t2 - t1) / 1000000)
    )
  }
}
