package model

/**
  * Created by agieg on 4/19/2017.
  */
class Grid(maxX: Int, maxY: Int, polygons:Polygon*) extends Iterable[Polygon] {
  // No two polygons should overlap. Intersecting polygons can always be represented as
  polygons.foreach(a => polygons.foreach(b => {
    if (a.overlaps(b)) {
      throw new IllegalArgumentException("No two polygons should overlap in the grid.")
    }
  }))

  // Make sure all polygons fall within the bounds of the grid.
  polygons.foreach(p => {
    p.foreach(line => {
      if (line.start().x() > maxX || line.start().x() < 0 || line.start().y() > maxY || line.start().y() < 0) {
        throw new IllegalArgumentException("A line was out of bound: "+line.toString)
      }
    })
  })

  // If any polygon in the grid overlaps a given line
  def overlaps(line: Line): Boolean = {
    this.foreach(poly => {
      if (poly.intersects(line)) {
        return true
      }
    })
    false
  }

  // gets a list of all points in the grid.
  def allPoints: List[Point] = {
    var points: List[Point] = List()
    for (x <- List.range(0, maxX + 1)) {
      for (y <- List.range(0, maxY + 1)) {
        points = points ++ List(new Point(x, y))
      }
    }
    points
  }

  // iterating over a grid iterates over its polygons
  override def iterator: Iterator[Polygon] = {
    polygons.iterator
  }
}
