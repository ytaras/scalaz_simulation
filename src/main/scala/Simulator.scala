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
