package simulations

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._
import scalaz._
import Scalaz._
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

class PriorityListTest extends FunSuite with Checkers
    with PriorityList with WorkItemGen {

  test("workitems are ordered by time in queue") {
    check { x: List[WorkItem] =>
      fromList(x) sliding(2) filter { _.size == 2 } map { w =>
        w(0) ?|? w(1)
      } forall { _ != Ordering.GT }
    }
  }

  test("laws are satisfied") {
    monoid.laws[PriorityList[WorkItem]].check
    equal.laws[PriorityList[WorkItem]].check
  }

  implicit val wiA = Arbitrary(wiG)
  implicit val plG = arbitrary[List[WorkItem]] map { fromList(_) }
  implicit val plA = Arbitrary(plG)
}
