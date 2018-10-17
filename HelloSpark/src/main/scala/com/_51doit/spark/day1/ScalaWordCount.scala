package com._51doit.spark.day1

import java.sql.DriverManager

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCount {

  def main(args: Array[String]): Unit = {


    //设置当前Hadoop的用户名
    //System.setProperty("HADOOP_USER_NAME", "root")

    //第一步：创建SparkContext
    val conf: SparkConf = new SparkConf().setAppName("ScalaWordCount").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    //第二步，指定以后从HDFS中读取数据创建RDD（神奇的大集合）
    //sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).sortBy(_._2, false).saveAsTextFile(args(1))
    val lines: RDD[String] = sc.textFile(args(0))

    //切分压平
    val func = (x: String) => x.split(" ").iterator
    val words: RDD[String] = lines.flatMap(func)

    //将单词和一组合在一起
    val wordAndOne: RDD[(String, Int)] = words.map((_, 1))


    //wordAndOne.aggregateByKey()
    //聚合
    val reduced: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _)

    //排序
    //val sorted: RDD[(String, Int)] = reduced.sortBy(_._2, false, 1)


    //sorted.takeOrdered(3)

    //将数据保存到HDFS
    reduced.saveAsTextFile(args(1))

    //数据很多
    //    sorted.foreach(t => {
    //      // 创建连接
    //      val conn = DriverManager.getConnection("", "", "")
    //      //执行插入
    //
    //      //关闭连接
    //      conn.close()
    //    })

//    sorted.foreachPartition(it => {
//      // 创建连接
//      val conn = DriverManager.getConnection("", "", "")
//
//      it.foreach( t => {
//        //执行插入
//      })
//      //关闭连接
//      conn.close()
//    })

    //最后释放资源
    sc.stop()


  }
}
