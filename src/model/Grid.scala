package model

/**
  * Created by agieg on 4/19/2017.
  */
class Grid(_maxX: Int, _maxY: Int, _polygons:Polygon*) extends Iterable[Polygon] {
  val maxX = new Rational(_maxX, 1)
  val maxY = new Rational(_maxY, 1)
  val polygons = _polygons
  val dimensions = (maxX, maxY)
  // No two polygons should overlap. Intersecting polygons can always be represented as
  polygons.foreach(a => polygons.foreach(b => {
    if (a.overlaps(b)) {
      throw new IllegalArgumentException("No two polygons should overlap in the grid.")
    }
  }))

  // Make sure all polygons fall within the bounds of the grid.
  polygons.foreach(p => {
    p.foreach(line => {
      if (line.start.x > maxX || line.start.x < new Rational(0, 1) || line.start.y > maxY || line.start.y < new Rational(0, 1) ||
              line.end.x > maxX || line.end.x < new Rational(0, 1) || line.end.y > maxY || line.end.y < new Rational(0, 1)) {
        throw new IllegalArgumentException("A line was out of bounds: "+line.toString)
      }
    })
  })

  // If any polygon in the grid overlaps a given line
  def overlaps(line: Line): Boolean = {
    var aPolygonContainsTheLine = false
    // Do this ugly loop for efficiency.
    this.foreach(poly => {
      if (line.midpoint.inside1(poly)) return true
      poly.foreach(side => {
        if (side == line && aPolygonContainsTheLine) {
          return true
        } else if (side == line) {
          aPolygonContainsTheLine = true
        }
        if (line.intersects(side.start) || line.intersects(side.end)) {
          return true
        }
        if ( line.intersects(side) ) {
          // return true if the line intersects any side of a polygon NOT including ends
          return true
        }
      })
    })
    false
  }



  // Gets all points connected to the given point by a line in the grid.
  def getNeighbors(point: Point): Set[Point] = {
    allLines.filter(line => line.hasEndPoint(point))
      .flatMap(line => List(line.start, line.end))
      .filter(p => p != point)
  }

  def getPolygonsContainingLine(line: Line): List[Polygon] = {
    polygons.filter(p => p.contains(line)).toList
  }

  def getPolygonsContainingPoint(point: Point): Set[Polygon] = {
    polygons.filter(p => p.contains(point)).toSet
  }

  // gets a list of all points in the grid.
  def allVertices: Set[Point] = {
    this.flatMap(poly => poly.vertices).toSet
  }

  def allLines: Set[Line] = {
    this.flatMap(poly => poly.sides).toSet
  }

  // iterating over a grid iterates over its polygons
  override def iterator: Iterator[Polygon] = {
    polygons.iterator
  }
}
