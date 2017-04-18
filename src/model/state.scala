package model

trait State {
	def successors(): List[State]
}