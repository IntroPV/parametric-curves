package ar.pablitar.intropv

import com.badlogic.gdx.Game

class ParametricCurves extends Game {
  override def create(): Unit = this.setScreen(new ParametricCurvesScreen())
}
