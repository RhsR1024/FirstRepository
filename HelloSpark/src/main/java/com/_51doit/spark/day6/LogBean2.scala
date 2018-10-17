package com._51doit.spark.day6

import scala.collection.mutable.ArrayBuffer

case class LogBean2(
                     event_type: Int,
                     pay_status: Int,
                     province: String,
                     city: String,
                     longitude: Double,
                     goods: Array[Good]
                   )