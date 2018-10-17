package com._51doit.spark.day6

import org.apache.spark.sql.{Dataset, SparkSession}

object SQLWordCount {

  def main(args: Array[String]): Unit = {

    //val conf = new SparkConf()

    val session: SparkSession = SparkSession.builder().appName(this.getClass.getSimpleName)
      .master("local[2]")
      .getOrCreate()

    //导入隐式转换
     import session.implicits._
    //创建Dataset
    val lines: Dataset[String] = session.read.textFile(args(0))

    val words: Dataset[String] = lines.flatMap(_.split(" "))

    //注册视图
    words.createTempView("v_words")

    var r = session.sql("SELECT COUNT(*) counts, value word FROM v_words GROUP BY word ORDER BY counts DESC")

    r.show()

    session.stop()





  }

}
