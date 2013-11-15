package simulations

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._
import scalaz._
import Scalaz._

class WorkItemCheck extends FunSuite with Checkers
    with WorkItemGen {
  test("workitems are ordered by time") {
    check { (x: WorkItem, y: WorkItem) => x ?|? y == x.time ?|? y.time }
  }
  implicit val wiA: Arbitrary[WorkItem] = Arbitrary(wiG)
}

trait WorkItemGen extends WorkItem {
  val wiG = arbitrary[Int] map { x => WorkItem(Time(x)) }
}
