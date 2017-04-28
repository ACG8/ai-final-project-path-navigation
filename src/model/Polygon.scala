package model

/**
  * Created by agieg on 4/19/2017.
  */
object Polygon {

  def convert_to_lines(lines: Seq[Point Tuple2 Point]): Seq[Line] = {
    lines.map {case (a,b) => new Line(a,b)}
  }

  // helper method that "rotates" as sequence e.g. (1, 2, 3, 4) => (2, 3, 4, 1)
  private def rotate[T](lst: Seq[T]): Seq[T] = {
    lst match {
      case head +: tail => tail++Seq(head)
      case head +: Seq() => Seq(head)
      case Seq() => Seq()
      case Nil => throw new IllegalArgumentException("Input cannot be Nil")
    }
  }
}
class Polygon(_sides: Line*) extends Iterable[Line] {
  val sides:Seq[Line] = _sides
  // Helper method to build polygon from points
  def this(points: List[Point]) {
    this( Polygon.convert_to_lines(points zip Polygon.rotate(points)):_* )
  }

  def ccw(A:Point,B:Point,C:Point): Boolean = (C.y-A.y)*(B.x-A.x) > (B.y-A.y)*(C.x-A.x)
  if (!sides
    .iterator.sliding(2).toList
    .map( pair => ccw(pair.head.start,pair.head.end,pair.last.end))
    .forall( b => b==ccw(sides.last.start,sides.last.end,sides.head.end))
  ) {
    throw new IllegalArgumentException("Polygons must be convex. To make concave polygons, combine two or more convex polygons.")
  }

  if (sides.length != 1 && sides.length < 3) {
    throw new IllegalArgumentException("Polygons must have at least 3 sides or exactly one side")
  }

  if ((sides.length == 1) && (sides.head.start != sides.head.end)) {
    throw new IllegalArgumentException("A polygon with one side must have that 'side' be a point.")
  }

  sides.foreach(a => {
    sides.foreach(b => {
      if (a.intersects(b)) {
        throw new IllegalArgumentException("No line should intersect with any other line a polygon, including the same line")
      }
    })
  })

  // If a polygon contains a line.
  def contains(line: Line): Boolean = {
    sides.contains(line)
  }

  // If a polygon contains a point.
  def contains(point: Point): Boolean = {
    sides.flatMap(line => List(line.start, line.end)).contains(point)
  }

  // The set of all points that make up the polygon.
  def vertices: Set[Point] = {
    this.flatMap(line => List(line.start, line.end)).toSet
  }

  override def iterator: Iterator[Line] = {
    sides.iterator
  }

  // Check to make sure that adjacent sides connect.
  val zippedSides: Seq[Line Tuple2 Line] = for ( (a, b) <- sides zip Polygon.rotate(sides) ) yield Tuple2(a, b)
  zippedSides.foreach { case (a, b) =>
    if (a.end.x != b.start.x) {
      throw new IllegalArgumentException("The X coordinates of two consecutive sides are not equal.")
    }
    if (a.end.y != b.start.y) {
      throw new IllegalArgumentException("The Y coordinates of two consecutive sides are not equal")
    }
  }

  def intersectsNotIncludingPoint(line: Line, point: Point, includeEnds: Boolean): Boolean = {
    this.filter(line => line.start != point && line.end != point)
      .map(pLine => line.intersects(pLine, includeEnds))
      .reduce(_ || _)

  }

  def intersects(line: Line): Boolean = {
    intersects(line, includeEnds = false)
  }

  // if a line intersects any of a polygons lines
  def intersects(line: Line, includeEnds: Boolean): Boolean = {
    this.map(pLine => line.intersects(pLine, includeEnds)).reduce(_ || _)

  }

  // if one polygon overlaps another
  def overlaps(polygon: Polygon): Boolean = {
    (for {p1 <- this.toList; p2 <- polygon.toList} yield (p1, p2)) // gets the cross product of lines in each polygon
      .map{ case (a, b) => a.intersects(b) }.reduce(_ || _)
  }

  // Two polygons are equal if all their lines are in the same order.
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Polygon => (obj.sides zip this.sides).map {case (a,b) => a == b}.reduce(_&&_)
      case _ => false
    }
  }


}
