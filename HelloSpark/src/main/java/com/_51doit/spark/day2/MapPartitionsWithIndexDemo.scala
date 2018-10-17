package com._51doit.spark.day2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object MapPartitionsWithIndexDemo {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(List(1,2,3,4,5,6,7,8,9))

    //功能：知道每个分区中究竟有哪些元素
    val func = (index: Int, partition: Iterator[Int]) => {
      partition.map(x => "[partID:" +  index + ", val: " + x + "]")
    }




    //rdd1.mapPartitionsWithIndex()


  }
}
