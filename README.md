To compile and run the project navigate to the top-level directory, "ai-final-project-path-navigation",
and run:

sbt compile

followed by:

sbt run

The project is set up to automatically run on all the test cases and prints results to stdout and
creates diagrams of the solutions and outputs them to the "output" directory.

All of our test cases are found in main.scala.tests.astarTests. There are "grid" environments containing
the m by n grids and the polygons main.scala.tests.grids. Each algorithm is run on each test using each
heuristic.