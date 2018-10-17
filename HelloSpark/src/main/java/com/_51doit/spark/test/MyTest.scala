package com._51doit.spark.test

import java.io.File

import com._51doit.spark.day6.{LogBean, LogBean2}
import com.alibaba.fastjson.JSON

import scala.io.Source


object MyTest {

  def main(args: Array[String]): Unit = {

    val line = Source.fromFile(new File("C:\\Users\\zx\\Desktop\\log.json")).mkString

    //val jSONObject = JSON.parseObject(line)
    //val p = jSONObject.getString("province")
    //println(p)

    //val bean: LogBean = JSON.parseObject(line, classOf[LogBean])

    val bean: LogBean2 = JSON.parseObject(line, classOf[LogBean2])

    println(bean.province)
    println(bean.goods.toBuffer)
  }

}
