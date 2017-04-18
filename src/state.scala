trait State {
	def successors(): List[State]
}