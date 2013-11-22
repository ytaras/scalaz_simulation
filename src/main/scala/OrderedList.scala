package simulations

trait OrderedListComponent {
  import scalaz.Order
  import scalaz.Monoid
  import scalaz.Equal
  import scalaz.syntax.std.list._
  import scalaz.std.list._
  import scalaz.syntax.foldable._
  import scalaz.syntax.order._
  import scalaz.syntax.equal._

  private def addOrdered[T](vs: List[T], v: T)(implicit o: Order[T]): List[T] =
    vs match {
      case x :: xs if x lt v => x :: addOrdered(xs, v)
      case _ => v :: vs
    }

  private def mergeOrdered[T](v1: List[T], v2: List[T])(implicit o: Order[T]): List[T] =
    (v1, v2) match {
      case (Nil, _) => v2
      case (_, Nil) => v1
      case (x :: xs, y :: ys) =>
        if (x lt y) x :: mergeOrdered(xs, v2)
        else y :: mergeOrdered(v1, ys)
    }


  object OrderedList {
    def empty[T](implicit o: Order[T]): OrderedList[T] = new OrderedList(List())
    def apply[T](xs: List[T])(implicit o: Order[T]): OrderedList[T] =
      new OrderedList(xs.foldLeft(List[T]()) { addOrdered(_, _) })(o)
  }

  implicit def orderedListInstances[T](implicit o: Order[T], e: Equal[T]) =
    new Monoid[OrderedList[T]] with Equal[OrderedList[T]] {
      override def zero = OrderedList.empty[T]
      override def append(v1: OrderedList[T], v2: => OrderedList[T]) =
        v1 merge v2
      override def equal(v1: OrderedList[T], v2: OrderedList[T]) =
        v1.l equals v2.l
    }

  class OrderedList[T] private ( val l: List[T])(implicit val o: Order[T]) {
    def add(v: T) = new OrderedList(addOrdered(l, v))(o)
    def merge(other: OrderedList[T]) = new OrderedList(mergeOrdered(l, other.l))
    def toList = l
    override def toString = s"OrderedList: ${l}"
  }
}
