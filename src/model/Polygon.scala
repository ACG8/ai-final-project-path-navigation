package model

/**
  * Created by agieg on 4/19/2017.
  */
class Polygon(sides: List[Line]) {
  if (sides.length < 3) {
    throw new IllegalArgumentException("Polygons must have at least 3 sides.")
  }

  sides.foreach(a => {
    sides.foreach(b => {
      if (a != b && a.intersects(b)) {
        throw new IllegalArgumentException("Two lines in")
      }
    })
  })

  val zippedSides: List[Line Tuple2 Line] = for ( (a, b) <- sides zip rotate(sides) ) yield Tuple2(a, b)

  zippedSides.foreach( tuple => {
    if (tuple._1.end().x() != tuple._2.start().x()) {
      throw new IllegalArgumentException("The X coordinates of two consecutive sides are not equal.")
    }
    if (tuple._1.end().y() != tuple._2.start().y()) {
      throw new IllegalArgumentException("The Y coordinates of two consecutive sides are not equal")
    }
  })

  sides.foreach(side => {
    throw IllegalArgumentException
  })

  def rotate(lst: List[Line]): List[Line] = {
    lst match {
      case head::tail => tail++List(head)
    }
  }
}
