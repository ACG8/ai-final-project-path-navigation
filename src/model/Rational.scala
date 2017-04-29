package model

/**
  * Created by agieg on 4/28/2017.
  */
object Rational {
  def max(r1: Rational, r2: Rational): Rational = {
    if (r1 > r2) r1 else r2
  }
  def min(r1: Rational, r2: Rational): Rational = {
    if (r1 < r2) r1 else r2
  }
}
class Rational(_num: Int, _denom: Int) {
    def this(n: Int) {
      this(n, 1)
    }

    def gcd(x: Int, y: Int): Int = {
      if(y == 0) x else gcd(y, x % y)
    }

  val g = gcd(_num,_denom)
  val num = _num/g
  val denom =  _denom/g

  def +(r2: Rational): Rational = {
    new Rational(this.num*r2.denom + r2.num*this.denom, this.denom*r2.denom)
  }

  def -(r2: Rational): Rational = {
    new Rational(this.num*r2.denom - r2.num*this.denom, this.denom*r2.denom)
  }

  def *(r2: Rational): Rational = {
    new Rational(this.num*r2.num, this.denom*r2.denom)
  }

  def /(r2: Rational): Rational = {
    new Rational(this.num*r2.denom, this.denom*r2.num)
  }

  def >(r2: Rational): Boolean = {
    this.num*r2.denom > this.denom*r2.num
  }

  def <(r2: Rational): Boolean = {
    this.num*r2.denom < this.denom*r2.num
  }

  def <=(r2: Rational): Boolean = {
    this < r2 || this == r2
  }

  def >=(r2: Rational): Boolean = {
    this > r2 || this == r2
  }

  def toDouble: Double = this.num.toDouble/this.denom

  def toFloat: Float = this.num.toFloat/this.denom


  def round: Int = math.round(this.toDouble).toInt

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case obj: Rational => this.num * obj.denom == this.denom * obj.num
      case _ => false
    }
  }

  override def toString: String = this.toDouble.toString
}
