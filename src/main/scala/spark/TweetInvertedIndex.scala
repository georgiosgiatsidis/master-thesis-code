package spark

package com.tp.spark.core

import org.apache.spark.{SparkContext, SparkConf}

case class Tweet(content: String)

object TweetInvertedIndex {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Inverted index")
      .setMaster("local[*]")

    val sc = SparkContext.getOrCreate(conf)

    val tweets = sc.wholeTextFiles("data/tweets/*.csv")
      .flatMap {
        case (_, txt) => txt.split('\n').drop(1)
      }
      .map(line => Tweet(line.split(',')(2)))


    tweets
      .flatMap(tweet => tweet
        .content
        .split(" ")
        .filter(str => str.startsWith("#") && str.length > 1)
        .map(h => (h, List(tweet))))
      .reduceByKey((_ ++ _))
      .saveAsTextFile("output")
  }

}