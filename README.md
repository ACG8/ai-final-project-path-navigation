# ai-final-project-path-navigation
Final project for the Spring 2017 AI course. Finding the shortest path on a plane with polygonal obstacles.


#2.2 Path Planning
Consider the problem of finding the shortest path between two points on a plane that has polygonal
obstacles, as shown in Figure 1. This is an idealization of the problem a robot has to solve to
navigate its way around a crowded environment.

Suppose the state space consists of a m × n grid of points in the plane. For simplicity, but
without loss of generality if we admit one-vertex polygons, assume that the robot always starts on
a polygon vertex in the grid and its goal is to reach some other polygon vertex.
It is not difficult to show that the shortest path from one polygon vertex to any other polygon
vertex in the grid must consist of straight-line segments joining a number of polygon vertices.

Keeping this in mind, define the necessary functions to implement the search problem, including a
successor function that takes a vertex v as input and returns the set of vertices that can be reached
from v in a straight line, without intersecting any other vertices or internal points of a polygon in
the grid. In this setting a solution is a (possibly non-straight line) between an initial and a final
point, described by the sequence of its segments. The cost of a solution is its length.

##Part 1 
Implement the search algorithm first using the greedy best-first strategy and then any
search strategy among A*, IDA* and SMA*. Except for the data structures that implement a state,
the heuristic function and the procedures that manipulate states, your implementation should be
independent from the specific domain.

Try at least two heuristic functions for your two chosen strategies. You can use any admissible
heuristics you deem appropriate for the problem, but you should convincingly argue in your report
that the heuristics is in fact admissible.

Use your implementation to run a number of test cases (no less than 20) of different complexity
in terms of number, shape and size of the polygons, and difficulty of the solution. Make sure your
test cases include non-convex polygons as well.

For each test case report the same kind of statistics required in the 8-puzzle project.

##Part 2
Now implement the search algorithm using k-look-ahead hill-climbing. This is a variation
of hill-climbing that generates the set D of all the descendants of the current node that are at depth
dc + k in the search tree, where dc is the depth of the current node. Then it chooses as the next
node the immediate successor of the current node that is the ancestor of the best node in D.

3 Note
that standard hill-climbing is the same as 1-look-ahead hill-climbing.
Run this implementation on the same test cases you selected for Part 1 and collect the same
sort of statistics for a number of values of the parameter k.
Provide a comparative evaluation of all the various configurations of strategies and heuristics
that you tried in Part 1 with the ones in Part 2. Focus on computational and costs and on the
quality of the solution found.

##Hints
To implement the various domain specific functions in this problem, it is useful to make the following
observations.

* A point B is unreachable from a point A if and only if there is a polygon on the grid one of
whose sides intersects with an internal point of the segment AB but does not coincide with
AB.
* The unique straight line over two points (a1, a2) and (b1, b2) of the Cartesian plane is denoted by the equation
(b2 − a2)x1 − (b1 − a1)x2 = a1(b1 − a2) − a2(b1 − a1)
in the unkowns x1 (for the first coordinate) and x2 (for the second coordinate).
* Two lines a11x1 + a12x2 = b1 and a21x1 + a22x2 = b2 are parallel iff a22a11 = a21a12.
* Two lines a11x1 + a12x2 = b1 and a21x1 + a22x2 = b2 that are not parallel intersect at the
point
(a22a11 − a21a12)
−1
* (a22b1 − a12b2, a11b2 − a21b1).