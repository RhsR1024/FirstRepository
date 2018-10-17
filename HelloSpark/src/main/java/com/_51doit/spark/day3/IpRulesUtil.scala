package com._51doit.spark.day3

import java.io.{BufferedReader, InputStreamReader}
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataInputStream, FileSystem, Path}

import scala.collection.mutable.ArrayBuffer

object IpRulesUtil {

  //初始化一个集合
  val ipRules = new ArrayBuffer[(Long, Long, String)]()

  //hdfs的读取数据的过程
  val conf = new Configuration()
  val fs: FileSystem = FileSystem.get(URI.create("hdfs://node-1.51doit.com:9000"), conf)
  val in: FSDataInputStream = fs.open(new Path("/iprules/ip.txt"))
  val reader = new BufferedReader(new InputStreamReader(in))

  var line = reader.readLine()

  while (line != null) {
    val fields = line.split("[|]")
    val startNum = fields(2).toLong
    val endNum = fields(3).toLong
    val province = fields(6)
    ipRules.append((startNum, endNum, province))

    line = reader.readLine()
  }

  def binarySearch(ip: Long) : String = {
    var low = 0
    var high = ipRules.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= ipRules(middle)._1) && (ip <= ipRules(middle)._2))
        return ipRules(middle)._3
      if (ip < ipRules(middle)._1)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    "未知"
  }






}
