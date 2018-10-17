package com._51doit.spark.day3

import com._51doit.spark.utils.MyUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object IpLocation {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    //指定以后从哪里读取数据创建RDD
    val accessLog: RDD[String] = sc.textFile(args(0))

    //对数据进行处理
    val provinceAndOne: RDD[(String, Int)] = accessLog.map(line => {
      val fields = line.split("[|]")
      val ip = fields(1)
      val ipNum = MyUtil.ip2Long(ip)
      val province = IpRulesUtil.binarySearch(ipNum)
      (province, 1)
    })

    //聚合
    val reudced: RDD[(String, Int)] = provinceAndOne.reduceByKey(_+_)

    reudced.saveAsTextFile(args(1))

    sc.stop()



  }

}
