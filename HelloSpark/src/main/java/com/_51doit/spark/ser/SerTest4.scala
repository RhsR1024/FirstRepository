package com._51doit.spark.ser

import java.net.InetAddress

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/6/25.
  */
object SerTest4 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val lines = sc.textFile(args(0))

    val result = lines.map(word => {
      val r = new Rules2
      //函数的执行是在Executor执行的（Task中执行的）
      val hostname = InetAddress.getLocalHost.getHostName
      val threadName = Thread.currentThread().getName
      //Rules.rulesMap在哪一端被初始化的？
      val mp = r.rulesMap

      val verson = mp(word)

      (hostname, threadName, word + verson ,r)
    })

    result.saveAsTextFile(args(1))

    sc.stop()

  }

}
