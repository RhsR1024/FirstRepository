package com._51doit.spark.ser



import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/7/24.
  */
object SerializableTest {

  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setAppName("SerializableTest").setMaster("local[*]")

    val sc = new SparkContext(conf)

    //这个变量是在哪一端定义的？（Driver端）
    val myRules = new MyRules

    val rdd = sc.makeRDD(List(("Hadoop"), ("Spark"), ("Storm")))

    val result: RDD[(String, String)] = rdd.map(word => {
      //函数中的业务逻辑是在哪里执行的呢？（Executor中的Task执行的）
      val v = myRules.rulesMap.getOrElse(word, "no-found")
      (word, v)
    })
    val r= result.collect()

    println(r.toBuffer)

    sc.stop()


  }

}

class MyRules extends Serializable {


  val rulesMap = Map[String, String]("Hadoop" -> "nice", "Spark" -> "perfect", "Storm" -> "soso")



}