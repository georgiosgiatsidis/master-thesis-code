package standardscala

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.math.random
import utills.Utils

object Pi {
  implicit val ec = ExecutionContext.global

  def await[T](f: Future[T]): T = Await.result(f, Duration.Inf)

  def pi(n: Int): Future[Double] =
    Future {
      var count = 0
      (1 to n).foreach { _ =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x * x + y * y <= 1) count += 1
      }
      4.0 * count / (n - 1)
    }

  def piParallel(n: Int, parallelism: Int): Future[Double] =
    Future
      .traverse(List.fill(parallelism)(n / parallelism))(pi)
      .map(_.sum / parallelism)

  def main(args: Array[String]): Unit = {
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt

    Utils.measureTime(await(pi(n)), "Sequential:")
    Utils.measureTime(await(piParallel(n, slices)), "Parallel:")
  }

}