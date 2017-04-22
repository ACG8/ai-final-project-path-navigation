package model

/**
  * Created by agieg on 4/18/2017.
  */
class PathState(grid: Grid, _position: Point, goals: Point*) extends State {
  def isGoalState(): Boolean = goals.contains(position())

  // The second value is the cost of reaching the state from the current state.
  def successors(): List[(State,Double)] = {
    var successors: List[(State, Double)] = List()
    grid.allPoints.foreach(dest => {
      val line = new Line(position(), dest)
      if (!grid.overlaps(line)) {
        successors = successors ++ List((new PathState(grid, dest, goals:_*), line.length()))
      }
    })
    successors
  }
  def position() = _position
  def asString: String = position().toString
}
