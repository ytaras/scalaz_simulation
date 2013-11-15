package simulations


trait WorkItem extends WithTags {
  import scalaz.Order
  import scalaz.syntax.order._

  case class WorkItem(time: Time)
  val first = WorkItem(Time(1))

  implicit val wiOrder: Order[WorkItem] = Order.orderBy { _.time  }
  // Won't compile - we have to tag value as Time
  // val failing = WorkItem(2)
}

trait WithTags {
  import scalaz.{Tag, @@}
  import scalaz.Order

  // TODO Seems like a lot of fuzz to implement such a simple thing..
  // are there macros?
  object Tags {
    sealed trait Time
    val Time = Tag.of[Time]
  }

  type Time = java.lang.Integer @@ Tags.Time
  implicit object Time extends Order[Time] {
    def apply[A](a: A): (A @@ Tags.Time) = Tag(a)
    def order(x: Time, y: Time) = Order[Int].order(x, y)
  }

}

trait Queue {
  import scalaz.Order
  import scalaz.Foldable
  import scalaz.syntax.order._
  type Queue[A] = List[A]

  // TODO Reverse order of workitem and put greater things in front
  def append[A](q: Queue[A], i: A)(implicit o: Order[A]): Queue[A] = q match {
    case h :: xs if i gt h => h :: append(xs, i)
    case _ => i :: q
  }

  def listToQueue[F[_], A](x: F[A])(implicit o: Order[A], f: Foldable[F]) =
    f.
}
