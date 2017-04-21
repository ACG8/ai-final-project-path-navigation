package model

/**
  * Created by agieg on 4/19/2017.
  */
class Polygon(sides: Line*) extends Iterable[Line] {
  if (sides.length < 3) {
    throw new IllegalArgumentException("Polygons must have at least 3 sides.")
  }

  sides.foreach(a => {
    sides.foreach(b => {
      if (a.intersects(b)) {
        throw new IllegalArgumentException("No line should intersect with any other line a polygon, including the same line")
      }
    })
  })

  override def iterator: Iterator[Line] = {
    sides.iterator
  }

  // Check to make sure that adjacent sides connect.
  val zippedSides: Seq[Line Tuple2 Line] = for ( (a, b) <- sides zip rotate(sides) ) yield Tuple2(a, b)
  zippedSides.foreach( tuple => {
    if (tuple._1.end().x() != tuple._2.start().x()) {
      throw new IllegalArgumentException("The X coordinates of two consecutive sides are not equal.")
    }
    if (tuple._1.end().y() != tuple._2.start().y()) {
      throw new IllegalArgumentException("The Y coordinates of two consecutive sides are not equal")
    }
  })

  def intersects(line: Line): Boolean = {
    this.foreach(pLine => {
      if (line.intersects(pLine)) {
        return false
      }
    })
    true
  }

  def overlaps(polygon: Polygon): Boolean = {
    polygon.foreach(aLine => {
      this.foreach(bLine => {
        if (aLine.intersects(bLine)) {
          return true
        }
      })
    })
    false
  }

  private def rotate(lst: Seq[Line]): Seq[Line] = {
    lst match {
      case head +: tail => tail++Seq(head)
      case head +: Seq() => Seq(head)
      case Seq() => Seq()
      case Nil => throw new IllegalArgumentException("Input cannot be Nil")
    }
  }
}
