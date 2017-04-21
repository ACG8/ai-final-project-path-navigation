package model

/**
  * Created by agieg on 4/19/2017.
  */
class Polygon(sides: Line*) {
  if (sides.length < 3) {
    throw new IllegalArgumentException("Polygons must have at least 3 sides.")
  }

  val rotatedOnce: Seq[Line] = rotate(sides)
  var rotatedAround: Seq[Line] = Nil
  for (a <- 1 until sides.length) {
    if (rotatedAround == Nil) {
      rotatedAround = rotate(sides)
    } else {
      rotatedAround = rotate(rotatedAround)
    }
  }

  (rotatedOnce, sides, rotatedAround).zipped.toList.foreach(tuple => {
    sides.foreach(side => {
      if (side != tuple._1 && side != tuple._2 && side != tuple._3 && tuple._2.intersects(side)) {
        throw new IllegalArgumentException("Two non-adjacent sides intersect each other: "+
          tuple._2.toString + ", " + side.toString)
      }
    })
  })


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

  private def rotate(lst: Seq[Line]): Seq[Line] = {
    lst match {
      case head +: tail => tail++Seq(head)
      case head +: Seq() => Seq(head)
      case Seq() => Seq()
      case Nil => throw new IllegalArgumentException("Input cannot be Nil")
    }
  }
}
