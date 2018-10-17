package com._51doit.spark.day4

class Boy(val age: Int, val fv: Double) extends Ordered[Boy] with Serializable {
  override def compare(that: Boy): Int = {
    if(this.fv == that.fv) {
      this.age - that.age
    } else {
      -(this.fv -that.fv).toInt
    }

  }

  override def toString: String = s"age: $age, fv: $fv"
}
