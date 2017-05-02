package model

/**
  * Created by Ananda on 4/18/2017.
  *
  * Every state must know:
  * 1. Which states are reachable and what their costs are relative to the current state
  * 2. If they are a goal state or not
  */

trait State[T <: State[T]] {
	def successors: List[(T,Double)]
	def isGoalState: Boolean
	def toString: String
	def size: Int
	override def equals(obj: scala.Any): Boolean
}