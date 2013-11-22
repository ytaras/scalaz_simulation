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

  sealed trait OrderedList[T] {
    def toList: List[T]
    def merge(order: OrderedList[T]): OrderedList[T]
    def add(v: T): OrderedList[T]
  }

  object OrderedList {
    def empty[T](implicit o: Order[T]): OrderedList[T] = L(Nil)

    def apply[T](xs: List[T])(implicit o: Order[T]): OrderedList[T] =
      xs.foldLeft(empty(o)) { (x, y) => x add y }
  }

  private case class L[T](val toList: List[T])(implicit o: Order[T])
      extends OrderedList[T] {
    def merge(other: OrderedList[T]) =
      toList.foldLeft(other) { _ add _ }
    def add(v: T) = L(addOrdered(toList, v))

    private def addOrdered(l: List[T], v: T): List[T] = l match {
      case x :: xs if x lt v => x :: addOrdered(xs, v)
      case _ => v :: l
    }
  }

  implicit def orderedListInstances[T](implicit o: Order[T], e: Equal[T]) =
    new Monoid[OrderedList[T]] with Equal[OrderedList[T]] {
      override def zero = OrderedList.empty[T]
      override def append(v1: OrderedList[T], v2: => OrderedList[T]) =
        v1 merge v2
      override def equal(v1: OrderedList[T], v2: OrderedList[T]) =
        v1.toList equals v2.toList
    }

}
