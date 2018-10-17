package com._51doit.spark.ser



import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
  * Created by zx on 2017/6/25.
  */
class Task extends Serializable {

}

object Task {

  def main(args: Array[String]): Unit = {

    val t = new Task

    //new完之后打印的
    println(t)

    val oos = new ObjectOutputStream(new FileOutputStream("./t"))

    oos.writeObject(t)
    oos.flush()
    oos.close()

    val ois1 = new ObjectInputStream(new FileInputStream("./t"))
    val o1 = ois1.readObject()
    println(o1)
    ois1.close()


    val ois2 = new ObjectInputStream(new FileInputStream("./t"))
    val o2 = ois2.readObject()
    println(o2)
    ois2.close()

  }

}
