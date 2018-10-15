package ar.pablitar.intropv

import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Bezier, Vector2}
import ar.com.pablitar.libgdx.commons.extensions.PathExtensions._
import ar.com.pablitar.libgdx.commons.extensions.InputExtensions._
import ar.com.pablitar.libgdx.commons.extensions.VectorExtensions._
import ar.com.pablitar.libgdx.commons.rendering.Renderers
import ar.com.pablitar.libgdx.commons.traits.{Positioned, SimplePositioned}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

import scala.collection.JavaConverters._

class ParametricCurvesScreen() extends ScreenAdapter {

  val renderers = new Renderers()
  val POINT_RADIUS = 16

  val SCREEN_HEIGHT = 720

  val curve : Bezier[Vector2] = new Bezier[Vector2](
    new Vector2(20,20),
    new Vector2(200, 100),
    new Vector2(300, 200),
    new Vector2(400, 400)
  )

  val pathTraveler = new PathTraveler(curve)

  override def render(delta: Float): Unit = {
    update(delta)
    draw()
  }

  var dragStatus: DragStatus = NotDragging

  def startDraggingAt(position: Vector2): Unit = {
    position.y = SCREEN_HEIGHT - position.y
    Gdx.app.log("Dragging", s"Started Dragging at: $position")
    curve.points.asScala.find { aPoint => (position - aPoint).len2() < POINT_RADIUS * POINT_RADIUS }.foreach { point =>
      dragStatus = Dragging(this, point)
    }
  }

  def update(delta: Float): Unit = {
    Gdx.input.touchPositionOption().foreach(startDraggingAt)
    dragStatus.update(delta)
    pathTraveler.update(delta)
  }

  def draw() = {
    renderers.withRenderCycle() {
      curve.drawOn(renderers)

      renderers.withShapes(ShapeType.Filled) { sr =>
        renderTraveler(sr)

        curve.points.forEach { point =>
          sr.setColor(Color.LIGHT_GRAY)
          sr.circle(point.x, point.y, POINT_RADIUS)
          sr.setColor(Color.WHITE)
          sr.circle(point.x, point.y, POINT_RADIUS - 4)
        }
      }
    }
  }

  private def renderTraveler(sr: ShapeRenderer) = {
    val oldMatrix = sr.getTransformMatrix.cpy()

    sr.translate(pathTraveler.x, pathTraveler.y, 0)
    sr.rotate(0, 0, 1, pathTraveler.direction)
    sr.setColor(Color.GREEN)
    sr.rect(- 15, - 15, 30, 30)

    sr.setTransformMatrix(oldMatrix)
  }
}
