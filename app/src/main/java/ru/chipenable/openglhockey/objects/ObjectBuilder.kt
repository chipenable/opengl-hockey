package ru.chipenable.openglhockey.objects

import android.opengl.GLES20.*
import ru.chipenable.openglhockey.util.Circle
import ru.chipenable.openglhockey.util.Cylinder
import ru.chipenable.openglhockey.util.Point

/**
 * Created by Pavel.B on 03.06.2018.
 */

typealias DrawCommand = () -> Unit

class GeneratedData(val vertexData: FloatArray, val drawList: MutableList<DrawCommand>)

private class ObjectBuilder(sizeInVertices: Int) {

    private val FLOATS_PER_VERTEX = 3
    private val vertexData = FloatArray(sizeInVertices * FLOATS_PER_VERTEX)
    private var offset = 0
    private val drawList = mutableListOf<DrawCommand>()

    fun appendCircle(circle: Circle, numPoints: Int) {
        val startVertex = offset / FLOATS_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)

        vertexData[offset++] = circle.center.x
        vertexData[offset++] = circle.center.y
        vertexData[offset++] = circle.center.z

        for (i in 0 .. numPoints){
            val angleInRadians = (i.toFloat() / numPoints.toFloat()) * (Math.PI.toFloat() * 2f)
            vertexData[offset++] = circle.center.x + circle.radius * Math.cos(angleInRadians.toDouble()).toFloat()
            vertexData[offset++] = circle.center.y
            vertexData[offset++] = circle.center.z + circle.radius * Math.sin(angleInRadians.toDouble()).toFloat()
        }

        drawList.add{
            glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices)
        }
    }

    fun appendOpenCyclinder(cylinder: Cylinder, numPoints: Int){
        val startVertex = offset / FLOATS_PER_VERTEX
        val numVertices = sizeOfOpenCylinderInVertices(numPoints)
        val yStart = cylinder.center.y - (cylinder.height / 2f)
        val yEnd = cylinder.center.y + (cylinder.height / 2f)

        for (i in 0 .. numPoints){
            val angleInRadians = (i.toFloat() / numPoints.toFloat()) * (Math.PI.toFloat() * 2f)
            val xPosition = cylinder.center.x + cylinder.radius * Math.cos(angleInRadians.toDouble()).toFloat()
            val zPosition = cylinder.center.z + cylinder.radius * Math.sin(angleInRadians.toDouble()).toFloat()

            vertexData[offset++] = xPosition
            vertexData[offset++] = yStart
            vertexData[offset++] = zPosition

            vertexData[offset++] = xPosition
            vertexData[offset++] = yEnd
            vertexData[offset++] = zPosition
        }

        drawList.add{
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices)
        }
    }

    fun build()
       = GeneratedData(vertexData, drawList)
}

fun sizeOfCircleInVertices(numPoints: Int)
        = 1 + (numPoints + 1)

fun sizeOfOpenCylinderInVertices(numPoints: Int)
        = (numPoints + 1) * 2

fun createPuck(puck: Cylinder, numPoints: Int): GeneratedData {
    val size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints)
    val builder = ObjectBuilder(size)

    val puckTop = Circle(puck.center.translateY(puck.height / 2f), puck.radius)
    builder.appendCircle(puckTop, numPoints)
    builder.appendOpenCyclinder(puck, numPoints)

    return builder.build()
}

fun createMallet(center: Point, radius: Float, height: Float, numPoints: Int): GeneratedData {
    val size = sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2
    val builder = ObjectBuilder(size)

    val baseHeight = height * 0.25f
    val baseCircle = Circle(center.translateY(-baseHeight), radius)
    val baseCylinder = Cylinder(baseCircle.center.translateY(-baseHeight / 2f), radius, baseHeight)

    builder.appendCircle(baseCircle, numPoints)
    builder.appendOpenCyclinder(baseCylinder, numPoints)

    val handleHeight = height * 0.75f
    val handleRadius = radius / 3f

    val handleCircle = Circle(center.translateY(handleHeight * 0.5f), handleRadius)
    val handleCylinder = Cylinder(handleCircle.center.translateY(-handleHeight / 2f),
            handleRadius, handleHeight)

    builder.appendCircle(handleCircle, numPoints)
    builder.appendOpenCyclinder(handleCylinder, numPoints)

    return builder.build()
}