import model.PathState
import tests.modelTests

object main {
  def main(args: Array[String]): Unit = {
    val x = new PathState()
    astar.astar(x, y => 1.0)
    runTests()
  }

  def runTests(): Unit = {
    modelTests.runTests()
  }
}

