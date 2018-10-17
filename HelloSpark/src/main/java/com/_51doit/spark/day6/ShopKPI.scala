package com._51doit.spark.day6

import com.alibaba.fastjson.{JSON, JSONException}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

object ShopKPI {

  //log4j
  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    val isLocal = args(0).toBoolean
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    if(isLocal) {
      conf.setMaster("local[2]")
    }
    val sc = new SparkContext(conf)

    //指导以后从哪里读取数据
    val lines = sc.textFile(args(1))



    //解析json字符串
    val logBeanRDD: RDD[LogBean2] = lines.map(line => {
      var logBean: LogBean2 = null
      try {
        logBean= JSON.parseObject(line, classOf[LogBean2])
      } catch {
        case e: JSONException => {
          //将错误的数据保存到log中
          logger.error("error josn is : " + line)
        }
      }
      logBean
    })

    val filteredRDD = logBeanRDD.filter(_ != null)

    val r = filteredRDD.collect()

    println(r.toBuffer)

    sc.stop()

  }
}
