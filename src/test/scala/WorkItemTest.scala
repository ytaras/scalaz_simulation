package simulations

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._
import scalaz._
import Scalaz._

class Check extends Properties("WorkItem") with FunSuite with Checkers
    with WorkItem {

  test("workitems are ordered by time") {
    check { (x: WorkItem, y: WorkItem) => x ?|? y == x.time ?|? y.time }
  }

  val wiG = arbitrary[Int] map { x => WorkItem(Time(x)) }
  implicit val wiA = Arbitrary(wiG)

}
