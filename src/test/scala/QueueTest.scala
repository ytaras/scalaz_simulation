package simulations

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._
import scalaz._
import Scalaz._

class QueueCheck extends Properties("Queue") with FunSuite with Checkers
    with Queue with WorkItemGen {

  test("workitems are ordered by time in queue") {
    check { x: List[WorkItem] =>
      x.foldLeft(Monoid[List].zero)((x, y) => append(x, y)) sliding(2) filter { _.size == 2 } map { w => w(0) ?|? w(1) } forall { _ != Ordering.GT }
    }
  }
// TODO WorkQueue as monoid
  implicit val wiA = Arbitrary(wiG)

}
