package algorithms

import model.State

/**
  * Created by Richard on 4/24/2017
  * k-hill-climbing generates all the k-successors of the current state, and 
  * picks the one to expand with the greatest value of the heuristic function
  *
  */

object hillClimb {
	//start is the starting state and h is the heuristic function
//  def bestKDescendant[T <: State[T]](n : T, k : Int, h: T => Double): Double = k match {
//    case 0 => h(n)
//    case _ => {
//      n.successors().map{case(m : T, _ : Double) => bestKDescendant(m, k - 1, h) }.min
//    }
//  }
//  def minState[T <: State[T]]( x1 : (T, Double), x2 : (T, Double)) : (T, Double) = {
//    if (x1._2 < x2._2) x1 else x2
//  }
//  // next returns the best k-descendant of n with heuristic h
//  def next[T <: State[T]](n: T, k : Int, h: T => Double): (T, Double) = {
//    val x : List[(T, Double)] = n.successors()
//    x.map{ case (m: T, _ : Double) => (m, bestKDescendant(m, k-1, h) ) }
//      .reduceLeft{ minState }
//  }
//  def hillClimb[T <: State[T]](start: T, k: Int, h: T => Double): List[T] = {
//	  def hillClimbHelper(h: T => Double, k: Int, path: List[T]): List[T] = {
//      val nxt = next(path.head, k, h)
//      println("The next state is " + nxt._1.toString)
//      println("The value of the heuristic on the k-descendant is " + nxt._2)
//      println("The value of the heuristic on state itself is " + h(nxt._1))
//      nxt match {
//        case (s, _) if s.isGoalState  => (s :: path).reverse
//        case (s, heur) => /* some test */ hillClimbHelper(h, k, s :: path)
//      }
//    }
//    hillClimbHelper(h, k, List(start))
//  }

}
