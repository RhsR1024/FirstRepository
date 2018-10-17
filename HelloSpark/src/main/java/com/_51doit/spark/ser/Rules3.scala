package com._51doit.spark.ser

import java.net.InetAddress

/**
  * Created by zx on 2017/6/25.
  */
object Rules3 {

  val rulesMap = Map("Hadoop" -> 1, "Spark" -> 2, "Flink" -> 3)

  @transient
  val i : Int = 1000

  val hostname = InetAddress.getLocalHost.getHostName

  println(hostname + "@@@@@@@@@@@@@@@@")

}
