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

class OrderedListTest extends FunSuite with Checkers
    with OrderedListComponent {

  def isOrdered[T](x: OrderedList[T])(implicit o: Order[T]) =
    x.toList.sliding(2) filter { _.size == 2 } map { x =>
      x(0) ?|? x(1)
    } forall { _ != Ordering.GT }

  test("generated lists are ordered") {
    check { x: OrderedList[Long] => isOrdered(x) }
  }
  test("merged lists are ordered") {
    check { (x: OrderedList[Long], y: OrderedList[Long]) =>
      isOrdered(x merge y)
    }
  }

  test("laws are satisfied") {
    monoid.laws[OrderedList[Long]].check
    equal.laws[OrderedList[Long]].check
  }

  implicit def genOrdered: Arbitrary[OrderedList[Long]] =
    Arbitrary {
      arbitrary[List[Long]] map { OrderedList(_) }
    }

}
