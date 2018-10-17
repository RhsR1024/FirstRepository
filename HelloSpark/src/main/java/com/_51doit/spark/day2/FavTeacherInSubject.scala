package com._51doit.spark.day2

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FavTeacherInSubject {

  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    val lines: RDD[String] = sc.textFile(args(0))

    //处理数据
    val subjectTeacherAndOne: RDD[((String, String), Int)] = lines.map(line => {
      val teacher = line.substring(line.lastIndexOf("/") + 1)
      val url = new URL(line)
      val host = url.getHost
      val subject = host.substring(0, host.indexOf("."))
      ((subject, teacher), 1)
    })

    //聚合
    val reduced: RDD[((String, String), Int)] = subjectTeacherAndOne.reduceByKey(_+_)

    //按照学科进行分组
    val grouped: RDD[(String, Iterable[((String, String), Int)])] = reduced.groupBy(_._1._1)

    //组内排序
    val sorted: RDD[(String, List[((String, String), Int)])] = grouped.mapValues(_.toList.sortBy(-_._2).take(2))

    //把数据保存到hdfs
    sorted.saveAsTextFile(args(1))

    sc.stop()

  }

}
