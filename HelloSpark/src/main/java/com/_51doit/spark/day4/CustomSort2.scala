package com._51doit.spark.day4

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CustomSort2 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    //先按照颜值的从高到低进行排序，如果颜值相等，年龄小的排在前面
    val rdd: RDD[(String, Int, Double)] = sc.makeRDD(Array(("laoduan", 30, 99.0), ("laoyang", 28, 99.0), ("laozhao", 28, 9999.9)))


    val sorted: RDD[(String, Int, Double)] = rdd.sortBy(t => new Boy(t._2, t._3))

    val r = sorted.collect()

    println(r.toBuffer)

    sc.stop()

  }

}
