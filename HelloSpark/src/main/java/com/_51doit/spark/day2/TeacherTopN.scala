package com._51doit.spark.day2

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TeacherTopN {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    val lines: RDD[String] = sc.textFile(args(0))

    //对数据进行处理，切割
    val subjectTeacherAndOne: RDD[((String, String), Int)] = lines.map(line => {
      val teacher = line.substring(line.lastIndexOf("/") + 1)
      val url = new URL(line)
      val host = url.getHost
      val subject = host.substring(0, host.indexOf("."))
      ((subject, teacher), 1)
    })

    //聚合
    val reduced: RDD[((String, String), Int)] = subjectTeacherAndOne.reduceByKey(_+_)

    //排序
    val sorted: RDD[((String, String), Int)] = reduced.sortBy(_._2, false)

    //写入到HDFS中
    sorted.saveAsTextFile(args(1))

    //释放资源
    sc.stop()

  }

}
