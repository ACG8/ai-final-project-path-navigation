package model

import scala.collection.mutable

/**
  * Created by agieg on 4/19/2017.
  */

object Line {
  def getPoints(lines: List[Line]): Set[Point] = {
    lines.flatMap(l => List(l.start, l.end)).toSet
  }
}

class Line(_start: Point, _end: Point) {
  def start: Point = _start
  def end: Point = _end

  def vertices: Set[Point] = {
    Set(start, end)
  }

  def intersects(point:Point): Boolean ={
    val (x0,x1,y0,y1,xp,yp) = (start.x,end.x,start.y,end.y,point.x,point.y)
    val maxX = Rational.max(x0,x1)
    val minX = Rational.min(x0,x1)
    val maxY = Rational.max(y0,y1)
    val minY = Rational.min(y0,y1)
    (x0-x1)*(y0-yp)==(y0-y1)*(x0-xp) &&
      !(xp>maxX || xp < minX || yp > maxY || yp < minY) &&
      ( point != start) && (point != end)
  }

  def isParallel(line: Line): Boolean = {
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y
    val x3 = line.start.x
    val y3 = line.start.y
    val x4 = line.end.x
    val y4 = line.end.y
    val denominator = (x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4)

    // lines are parallel
    denominator == new Rational(0)
  }

  def length: Double = {
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y

    Math.sqrt(((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1)).toDouble)
  }

  def getIntersectionDenominatorAndXYNumerator(line: Line): (Rational, Rational, Rational) = {
    // Use x1-x4, y1-y4 to make it consistent with the source formula located here:
    // https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
    val x1 = this.start.x
    val y1 = this.start.y
    val x2 = this.end.x
    val y2 = this.end.y
    val x3 = line.start.x
    val y3 = line.start.y
    val x4 = line.end.x
    val y4 = line.end.y

    val denominator = (x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4)
    // Bits of these numerators could be factored out but I'm going to leave it as is so it's more explicit
    val xIntersectionNumerator = (x1*y2 - y1*x2)*(x3 - x4) - (x1 - x2)*(x3*y4 - y3*x4)
    val yIntersectionNumerator = (x1*y2 - y1*x2)*(y3 - y4) - (y1 - y2)*(x3*y4 - y3*x4)
    (denominator, xIntersectionNumerator, yIntersectionNumerator)
  }

//  def intersectsAtIntegerXY(line: Line): Boolean = {
//    val (denominator, xIntersectionNumerator, yIntersectionNumerator) = getIntersectionDenominatorAndXYNumerator(line)
//    (xIntersectionNumerator % denominator == 0) && (yIntersectionNumerator % denominator == 0)
//  }

  def getIntersection(line: Line): Point = {
    val (denominator, xIntersectionNumerator, yIntersectionNumerator) = getIntersectionDenominatorAndXYNumerator(line)
    // calculate the (x, y) intersection
    val xIntersection = xIntersectionNumerator/denominator
    val yIntersection = yIntersectionNumerator/denominator
    new Point(xIntersection, yIntersection)
  }

  def intersects(line: Line): Boolean = {
    intersects(line, includeEnds = false)
  }

  def intersects(line: Line, includeEnds: Boolean): Boolean = {
    // lines are parallel
    if (this.isParallel(line)) {
      return false
    }

    val intersection = getIntersection(line)
    val (xIntersection, yIntersection) = (intersection.x, intersection.y)
    // Decide if the X intersection occurs outside the line
    val thisGreaterX = Rational.max(this.start.x, this.end.x)
    val thisLesserX = Rational.min(this.start.x, this.end.x)

    val leq: (Rational, Rational) => Boolean = { (a, b) => a <= b }
    val lt: (Rational, Rational) => Boolean = { (a, b) => a < b }
    val geq: (Rational, Rational) => Boolean = { (a, b) => a >= b }
    val gt: (Rational, Rational) => Boolean = { (a, b) => a > b }

    val gtOp = if (includeEnds) gt else geq
    val ltOp = if (includeEnds) lt else leq
    // ends don't count as intersecting
    val thisXOutOfBounds = ltOp(xIntersection, thisLesserX) || gtOp(xIntersection, thisGreaterX)

    // Determine if the Y intersection occurs outside the line
    val thisGreaterY = Rational.max(this.start.y, this.end.y)
    val thisLesserY = Rational.min(this.start.y, this.end.y)
    val thisYOutOfBounds = ltOp(yIntersection, thisLesserY) || gtOp(yIntersection, thisGreaterY)

    val lineGreaterX = Rational.max(line.start.x, line.end.x)
    val lineLesserX = Rational.min(line.start.x, line.end.x)

    // ends don't count as intersecting

    val lineXOutOfBounds = ltOp(xIntersection, lineLesserX) || gtOp(xIntersection, lineGreaterX)

    // Determine if the Y intersection occurs outside the line
    val lineGreaterY = Rational.max(line.start.y, line.end.y)
    val lineLesserY = Rational.min(line.start.y, line.end.y)
    val lineYOutOfBounds = ltOp(yIntersection, lineLesserY) || gtOp(yIntersection, lineGreaterY)

    // If one point on the intersection is outside this line then the intersection occurs on the line.
    // One of the points can be outside the line.
    val intersectsThis = !(thisXOutOfBounds && thisYOutOfBounds)
    val intersectsLine = !(lineXOutOfBounds && lineYOutOfBounds)
    val intersects = intersectsThis && intersectsLine
    intersects
  }

  override def toString: String = {
    "line("+this.start.toString+", "+this.end.toString+")"
  }

  // If either end of the polygon is a given point.
  def hasEndPoint(point: Point): Boolean = {
    this.start == point || this.end == point
  }

  // Two lines are equal if they have the same points. Lines are not directed so the end point on one line
  // can be the start point on the other.
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Line => ((this.start == obj.start) &&  (this.end == obj.end)) ||
        ((this.start == obj.end) && (this.end == obj.start))
      case _ => false
    }
  }
}
