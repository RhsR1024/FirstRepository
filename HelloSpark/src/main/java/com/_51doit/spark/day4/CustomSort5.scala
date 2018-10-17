package com._51doit.spark.day4

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CustomSort5 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    //先按照颜值的从高到低进行排序，如果颜值相等，年龄小的排在前面
    val rdd: RDD[(String, Int, Double)] = sc.makeRDD(Array(("laoduan", 30, 99.0), ("laoyang", 28, 99.0), ("laozhao", 28, 9999.9)))

    //Ordering[((Double, Int)]安装指定的规则进行排序，泛型就是你指定的规则的泛型
    //on[(String, Int, Double)]未排序之前的样式
    implicit val rules = Ordering[(Double, Int)].on[(String, Int, Double)](o => (-o._3, o._2))
    val sorted = rdd.sortBy(t => t)


    val r = sorted.collect()

    println(r.toBuffer)

    sc.stop()

  }

}
