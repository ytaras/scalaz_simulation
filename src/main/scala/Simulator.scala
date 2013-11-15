package simulations

import scalaz.Order
import scalaz.{Tag, @@}
import scalaz.syntax.order._

trait WorkItem {
  case class WorkItem(time: Time)
  val first = WorkItem(Time(1))

  implicit val wiOrder: Order[WorkItem] = Order.orderBy { _.time  }
  // TODO Seems like a lot of fuzz to implement such a simple thing..
  // are there macros?
  object WITags {
    sealed trait Time
    val Time = Tag.of[Time]
  }

  type Time = java.lang.Integer @@ WITags.Time
  implicit object Time extends Order[Time] {
    def apply[A](a: A): (A @@ WITags.Time) = Tag(a)
    def order(x: Time, y: Time) = Order[Int].order(x, y)
  }

}

trait PriorityList {
  import scalaz.Monoid
  object PLTags {
    sealed trait Priority
    val Priority = Tag.of[Priority]
  }
  type PriorityList[A] = List[A] @@ PLTags.Priority
  class priorityList[A](implicit o: Order[A])
      extends Monoid[PriorityList[A]] { self =>
    import scalaz.Foldable
    import scalaz.syntax.foldable._
    import scalaz.std.list._
    def zero: PriorityList[A] = Tag(List())
    def append(a: PriorityList[A], b: => PriorityList[A]): PriorityList[A] =
      a.foldLeft(b)(insert)
  }
  def insert[A](b: PriorityList[A], a: A)(implicit o: Order[A]): PriorityList[A] =
    b.headOption match {
      case Some(h) if a gte h => Tag(h :: insert(Tag(b.tail), a))
      case _ => Tag(a :: b)
    }

  // TODO Find out how it's called in scalaz
  def fromList[A](a: List[A])(implicit m: Monoid[PriorityList[A]], o: Order[A]) =
    a.foldLeft(m.zero)(insert)

  implicit def priorityListMonoidInstance[A](implicit o: Order[A]) =
    new priorityList[A]
}
