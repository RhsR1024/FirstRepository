package com._51doit.spark.day3

import java.sql.{Connection, DriverManager, PreparedStatement}

import com._51doit.spark.utils.MyUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object IpLocation2 {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    //读取IP规则
    val ipRulesRDD: RDD[String] = sc.textFile(args(0))
    //整理IP规则数据
    val ipTuple: RDD[(Long, Long, String)] = ipRulesRDD.map(line => {
      val fields: Array[String] = line.split("[|]")
      val startNum = fields(2).toLong
      val endNum = fields(3).toLong
      val provicne = fields(6)
      (startNum, endNum, provicne)
    })
    //收集到Driver端
    val ipRulesInDriver: Array[(Long, Long, String)] = ipTuple.collect()
    //广播数据到Executor
    val ipRulesRefInDriver = sc.broadcast(ipRulesInDriver)

    //指定以后从哪里读取数据创建RDD
    val accessLog: RDD[String] = sc.textFile(args(1))

    //对数据进行处理
    val provinceAndOne: RDD[(String, Int)] = accessLog.map(line => {
      val fields = line.split("[|]")
      val ip = fields(1)
      val ipNum = MyUtil.ip2Long(ip)
      //在Executor中进行查找
      val ipRulesInExecutor: Array[(Long, Long, String)] = ipRulesRefInDriver.value
      //调用二分法查找
      val index = MyUtil.binarySearch(ipRulesInExecutor, ipNum)
      var province = "未知"
      if(index != -1) {
        province = ipRulesInExecutor(index)._3
      }
      (province, 1)
    })
    //聚合
    val reudced: RDD[(String, Int)] = provinceAndOne.reduceByKey(_+_)

    //将计算好的数据保存的MySQL中
    //reudced.saveAsTextFile(args(2))
    reudced.foreachPartition(MyUtil.data2MySQL)

    sc.stop()



  }

}
