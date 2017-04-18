import model.PathState

object main {
  def main(args: Array[String]): Unit = {
    val x = new PathState()
    astar.astar(x, y => 1.0)
    println("hello world")
  }
}

