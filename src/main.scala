import model.PathState

object main {
  def main(args: Array[String]): Unit = {
    val astar = new AStar()
    val x = new PathState()
    astar.astar(x, (y: PathState) => 1.0)
    println("hello world")
  }
}

