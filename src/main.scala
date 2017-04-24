import model.{Line, Point, Polygon, PathState}
import tests.{modelTests,astarTests}

object main {
  def main(args: Array[String]): Unit = {
    runTests()
  }

  def runTests(): Unit = {
    modelTests.runTests()
    astarTests.runTests()
  }
}

