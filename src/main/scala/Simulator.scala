package simulations


trait Simulator extends WithTags {

  case class WorkItem(time: Time)
  val first = WorkItem(Time(1))
  // Won't compile - we have to tag value as Time
  // val failing = WorkItem(2)
}

trait WithTags {
  import scalaz.{Tag, @@}

  object Tags {
    sealed trait Time
    val Time = Tag.of[Time]
  }

  type Time = java.lang.Integer @@ Tags.Time
  object Time { def apply[A](a: A): (A @@ Tags.Time) = Tag(a) }
}
