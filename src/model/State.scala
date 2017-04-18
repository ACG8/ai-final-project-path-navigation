package model

trait State {
	//def successors[T <: State](): List[T]
	def successors(): List[State]
}