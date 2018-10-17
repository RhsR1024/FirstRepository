package com._51doit.spark.day2

import java.net.URL

object TestSubString {


  def main(args: Array[String]): Unit = {

    val line = "http://bigdata.test.cn/laozhang"

    val teacher = line.substring(line.lastIndexOf("/") + 1)
    val url = new URL(line)
    val host = url.getHost
    val subject = host.substring(0, host.indexOf("."))

    println(subject)

  }
}
