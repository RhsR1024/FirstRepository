package com._51doit.spark.day4

class User(val name: String, val age: Int, val fv: Double) extends Ordered[User] with Serializable {
  override def compare(that: User): Int = {
    if(this.fv == that.fv) {
      this.age - that.age
    } else {
      -(this.fv -that.fv).toInt
    }

  }

  override def toString: String = s"name: $name, age: $age, fv: $fv"
}
