package com._51doit.spark.utils

import java.sql.{Connection, DriverManager, PreparedStatement}

object MyUtil {

  def ip2Long(ip: String): Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length) {
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def binarySearch(lines: Array[(Long, Long, String)], ip: Long): Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle)._1) && (ip <= lines(middle)._2))
        return middle
      if (ip < lines(middle)._1)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

  def data2MySQL(it: Iterator[(String, Int)]): Unit = {
    var conn: Connection = null
    var pstm: PreparedStatement = null
    try {
      conn = DriverManager.getConnection("jdbc:mysql://172.18.22.241:3306/bigdata?characterEncoding=UTF-8", "root", "123568")
      val pstm = conn.prepareStatement("INSERT INTO access_log VALUES (?, ?)")
      //把数据那出来
      it.foreach(t => {
        pstm.setString(1, t._1)
        pstm.setInt(2, t._2)
        pstm.executeUpdate()
      })
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (pstm != null) pstm.close()
      if (conn != null) conn.close()
    }
  }

}
