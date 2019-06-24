package com.packt.chapter1

class quicksort {
  def sorts(a: Array[Int], find: Int) = {
    var sorte = a.sorted

    def doit(start: Int = 0, stop: Int = sorte.length - 1): Boolean = {
      var pivot = start + (stop - start) / 2
      if (start > stop) false
      else if (a(start) == find) true
      else if (a(start) > find) doit(start, pivot - 1)
      else doit(pivot + 1, stop)
    }

    doit()
  }
}


object test extends App {
  var s = Array(2, 3, 1, 4, 5)

  var ss = new quicksort()
  val tr = ss.sorts(s, 2)
  println(tr.toString)
}


