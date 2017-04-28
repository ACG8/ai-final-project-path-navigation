package model

import java.awt.image.BufferedImage
import java.awt.{Graphics2D,Color,Font,BasicStroke}
import java.awt.geom._

/**
  * Created by agieg on 4/18/2017.
  *
  * We assume that there is only 1 goal state, as per the instructions
  */

class PathState(grid: Grid, _position: Point, _goal: Point) extends State[PathState] {
  val position:Point = _position
  val goal:Point = _goal

  def isGoalState: Boolean = goal == position

  // The second value is the cost of reaching the state from the current state.
  def successors: List[(PathState,Double)] = {
    grid.allVertices.filter(_ != position)
      .map(v => new Line(position, v)) // all lines from current position to possible vertices
      .filter(line => !grid.overlaps(line))
      .map(line => (new PathState(grid, line.end, goal), line.length)).toList
  }
  def String: String = position.toString

  def onSamePolygon(a: Point, b: Point): Boolean = {
    grid.getPolygonsContainingPoint(a).exists(poly => grid.getPolygonsContainingPoint(b).contains(poly))
  }

  def nonNeighborsOnSamePolygon(a: Point): Set[Point] = {
    grid.allVertices.filter(point => !isNeighbor(a, point) && onSamePolygon(a, point) && point != a)
  }

  def isNeighbor(a: Point, b: Point): Boolean = {
    grid.getNeighbors(a).contains(b)
  }

  override def toString: String = position.toString
}



object PathState {

  /**
    * Below are heuristic functions associated with PathState
    */
  val cartesianH: PathState => Double = (state) => {
    val (x0,y0) = (state.position.x,state.position.y)
    val (x1,y1) = (state.goal.x,state.goal.y)
    val (dx,dy) = (x1-x0,y1-y0)
    Math.sqrt(dx*dx+dy*dy)
  }

  /**
    * *code for drawing solutions is based on example from
    * https://raw.githubusercontent.com/otfried/cs109-scala/master/examples/drawing.scala
    *
    * This is not actually necessary, but helps us see what is happening
    */
  def drawSolution(title: String, grid:Grid, path:List[PathState]): Unit = {
    val start = path.head.position
    val end = path.last.position
    // TODO: Should adjust scale depending on size of inputs
    val scale = 1000/grid.dimensions._1
    val goalsize = 0.3

    // Size of image
    val size = (grid.dimensions._1*scale, grid.dimensions._2*scale)

    // create an image
    val canvas = new BufferedImage(size._1, size._2, BufferedImage.TYPE_INT_RGB)

    // get Graphics2D for the image
    val g = canvas.createGraphics()

    // clear background
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    // enable anti-aliased rendering (prettier lines and circles)
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)

    // draw the polygons
    g.setColor(Color.BLUE)
    for (poly <- grid)
      for (line <- poly) {
        g.setStroke(new BasicStroke())  // reset to default
        g.draw(new Line2D.Double(line.start.x*scale, line.start.y*scale, line.end.x*scale, line.end.y*scale))
      }



    // draw the path in green
    g.setColor(Color.GREEN)
    for (line <- path.iterator.sliding(2)) {
      g.setStroke(new BasicStroke())  // reset to default
      val p0 = line.head.position
      val p1 = line.last.position

      g.draw(new Line2D.Double(p0.x*scale, p0.y*scale, p1.x*scale, p1.y*scale))
    }

    // draw endpoints of path
    g.setColor(Color.GREEN) // green = path start
    g.fill(new Ellipse2D.Double((start.x-goalsize/2)*scale, (start.y-goalsize/2)*scale, goalsize*scale, goalsize*scale))
    g.setColor(Color.RED) // red = path end
    g.fill(new Ellipse2D.Double((end.x-goalsize/2)*scale, (end.y-goalsize/2)*scale, goalsize*scale, goalsize*scale))

    // draw caption
    g.setColor(Color.BLACK) // a darker green
    g.setFont(new Font("Batang", Font.PLAIN, scale/2))
    g.drawString(title, (0.1*scale).toFloat, size._2-(0.1*scale).toFloat)

    // done with drawing
    g.dispose()

    // write image to a file
    println("  [graphical representation saved in output/"++title++".png]")
    javax.imageio.ImageIO.write(canvas, "png", new java.io.File("output/"++title++".png"))
  }

}