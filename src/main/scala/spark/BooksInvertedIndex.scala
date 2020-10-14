package spark

import org.apache.spark.{SparkConf, SparkContext}

object BooksInvertedIndex {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Books Inverted Index").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val input = sc.wholeTextFiles("data/books/*")

    input
      .flatMap {
        case (path, txt) =>
          val words = txt.toLowerCase.split("[\\W_]+")
          words.map(word => {
            (word, Set(path.split("/").last))
          })
      }
      .reduceByKey(_ ++ _)
      .saveAsTextFile("output")

    sc.stop()
  }
}