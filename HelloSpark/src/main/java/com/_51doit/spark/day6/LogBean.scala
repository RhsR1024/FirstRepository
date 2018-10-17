package com._51doit.spark.day6

import scala.beans.BeanProperty

class LogBean {

  @BeanProperty
  var longitude: Double = _

  @BeanProperty
  var latitude: Double = _

  @BeanProperty
  var province: String = _
}
