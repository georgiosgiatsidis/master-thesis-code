package spark

import scala.math.random
import org.apache.spark.{SparkConf, SparkContext};

object Pi {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Pi").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt
    val count = sc.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y <= 1) 1 else 0
    }.reduce(_ + _)
    println(s"Pi is ${4.0 * count / (n - 1)}")
  }
}
