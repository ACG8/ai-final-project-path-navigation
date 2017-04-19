package model

/**
  * Created by Ananda on 4/18/2017.
  */

trait State {
	def successors(): List[(State,Double)]
}