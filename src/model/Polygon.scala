package model

/**
  * Created by agieg on 4/19/2017.
  */
object Polygon {

  def convert_to_lines(lines: Seq[Point Tuple2 Point]): Seq[Line] = {
    lines.map {case (a,b) => new Line(a,b)}
  }
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
  val sides = _sides
  def this(points: List[Point]) {
    this( Polygon.convert_to_lines(points zip Polygon.rotate(points)):_* )
  }

  if (sides.length != 1 && sides.length < 3) {
    throw new IllegalArgumentException("Polygons must have at least 3 sides.")
  }

  sides.foreach(a => {
    sides.foreach(b => {
      if (a.intersects(b)) {
        throw new IllegalArgumentException("No line should intersect with any other line a polygon, including the same line")
      }
    })
  })

  def inside(p :Point, maxX: Int, maxY: Int): Boolean = {
    val ray: Line = new Line(p, new Point(maxX+1, maxY+1))
    sides.map(side => ray.intersects(side)).count(x => x) % 2 == 0
  }

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

  def intersects(line: Line): Boolean = {
    this.map(pLine => line.intersects(pLine)).reduce(_ || _)
  }

  def overlaps(polygon: Polygon): Boolean = {
    (for {p1 <- this.toList; p2 <- polygon.toList} yield (p1, p2)) // gets the cross product of lines in each polygon
      .map{ case (a, b) => a.intersects(b) }.reduce(_ || _)
  }


}
