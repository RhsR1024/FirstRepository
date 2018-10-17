package com._51doit.spark.day5

import org.apache.spark.Partitioner

import scala.collection.mutable

class SubjectPartitioner(val subjects: Array[String]) extends Partitioner {

  //主构造器
  val rules = new mutable.HashMap[String, Int]()
  var i = 0
  for(sb <- subjects) {
    rules(sb) = i
    i += 1
  }

  override def numPartitions: Int = subjects.length

  override def getPartition(key: Any): Int = {
    //key是一个元祖，（subject，teacher）
    val subject = key.asInstanceOf[Tuple2[String, String]]._1
    rules(subject)
  }
}
