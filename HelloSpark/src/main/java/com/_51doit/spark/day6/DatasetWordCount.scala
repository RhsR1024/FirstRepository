package com._51doit.spark.day6

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, SparkSession}

object DatasetWordCount {

  def main(args: Array[String]): Unit = {

    //val conf = new SparkConf()

    val session: SparkSession = SparkSession.builder().appName(this.getClass.getSimpleName)
      .master("local[2]")
      .getOrCreate()

    //导入隐式转换
     import session.implicits._
     import org.apache.spark.sql.functions._
    //创建Dataset
    val lines: Dataset[String] = session.read.textFile(args(0))

    val words: Dataset[String] = lines.flatMap(_.split(" "))

    //DSL 领域特点语音，其实就是调用Dataset的方法
    val result = words.groupBy($"value" as "word").count().withColumnRenamed("count", "counts").sort($"counts" desc)

    result.show()

    session.stop()





  }

}
