package com._51doit.spark.day4

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CustomSort4 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    //先按照颜值的从高到低进行排序，如果颜值相等，年龄小的排在前面
    val rdd: RDD[(String, Int, Double)] = sc.makeRDD(Array(("laoduan", 30, 99.0), ("laoyang", 28, 99.0), ("laozhao", 28, 9999.9)))

    val userRDD: RDD[Person] = rdd.map(t => Person(t._1, t._2, t._3))


    implicit val sortRules = new Ordering[Person] {
      override def compare(x: Person, y: Person): Int = {
        if(x.fv == y.fv) {
          x.age - y.age
        } else {
          -(x.fv -y.fv).toInt
        }
      }
    }
    val sorted: RDD[Person] = userRDD.sortBy(p => p)

    val r = sorted.collect()

    println(r.toBuffer)

    sc.stop()

  }

}
