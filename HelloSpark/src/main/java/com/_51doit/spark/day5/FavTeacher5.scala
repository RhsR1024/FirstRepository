package com._51doit.spark.day5

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object FavTeacher5 {

  def main(args: Array[String]): Unit = {

    val isLocal = args(0).toBoolean
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    if(isLocal) {
      conf.setMaster("local[2]")
    }
    val sc = new SparkContext(conf)

    val lines = sc.textFile(args(1))

    val n = args(2).toInt

    //处理数据
    val subjectTeacherAndOne: RDD[((String, String), Int)] = lines.map(line => {
      val teacher = line.substring(line.lastIndexOf("/") + 1)
      val url = new URL(line)
      val host = url.getHost
      val subject = host.substring(0, host.indexOf("."))
      ((subject, teacher), 1)
    })

    //计算有多少个学科
    val subject: Array[String] = subjectTeacherAndOne.map(_._1._1).distinct().collect()
    //自定义分区器
    val partitioner = new SubjectPartitioner(subject)

    //聚合
    val reduced: RDD[((String, String), Int)] = subjectTeacherAndOne.reduceByKey(partitioner, _+_)

    //val partitioned: RDD[((String, String), Int)] = reduced.partitionBy(partitioner)

    val sorted = reduced.mapPartitions(it => {
      //支持排序的TreeSet创建好了

      val sorter = new mutable.TreeSet[((String, String),Int)]()(Ordering[Int].on(t => -t._2))
      //迭代数据
      it.foreach(t => {
        //将取出来的数据放入到Treeset中
        sorter += t
        if(sorter.size > n) {
          //取出treeset中的最小值
          val min = sorter.last
          sorter -= min
        }
      })
      sorter.iterator
    })

    val result = sorted.collect()

    println(result.toBuffer)

    sc.stop()


  }
}
