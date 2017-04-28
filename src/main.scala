import model.{Line, Point, Polygon, PathState}
import tests.{modelTests,astarTests}

object main {
  def main(args: Array[String]): Unit = {
    runTests()
  }

  def runTests(): Unit = {
    println("== MODEL TESTS ==")
    //modelTests.runTests()
    println("== A STAR TESTS ==")
    astarTests.runTests()
  }
}

