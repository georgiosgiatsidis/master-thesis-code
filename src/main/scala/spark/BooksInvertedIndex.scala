package spark

import org.apache.spark.SparkContext

object BooksInvertedIndex {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "BookInvertedIndex")
    val textFile = sc.wholeTextFiles("data/books/*")

    textFile
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