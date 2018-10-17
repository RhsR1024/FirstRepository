package com._51doit.spark.day5

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FavTeacher3 {


  def main(args: Array[String]): Unit = {

    //读取数据库
    val subjects = Array("bigdata", "javaee", "php")

    val isLocal = args(0).toBoolean
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    if(isLocal) {
      conf.setMaster("local[2]")
    }
    val sc = new SparkContext(conf)

    val lines = sc.textFile(args(1))

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

    //计算有多少个学科
    val subject: Array[String] = reduced.map(_._1._1).distinct().collect()

    val partitioner = new SubjectPartitioner(subject)

    val partitioned: RDD[((String, String), Int)] = reduced.partitionBy(partitioner)

    val sorted = partitioned.mapPartitions(_.toList.sortBy(-_._2).take(2).iterator)

    val result = sorted.collect()

    println(result.toBuffer)

    sc.stop()


  }
}
