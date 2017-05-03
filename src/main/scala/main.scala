package main.scala
import tests.modelTests
import tests.astarTests

object main {
  def main(args: Array[String]): Unit = {
    runTests()
  }

  def runTests(): Unit = {
    println("== MODEL TESTS ==")
    modelTests.runTests()
    println("== A STAR TESTS ==")
    astarTests.runTests()
  }
}

