package ru.chipenable.openglhockey.objects

import ru.chipenable.openglhockey.data.VertexArray
import ru.chipenable.openglhockey.programs.ColorShaderProgram
import ru.chipenable.openglhockey.util.Cylinder
import ru.chipenable.openglhockey.util.Point

/**
 * Created by Pavel.B on 03.06.2018.
 */
class Puck(radius: Float, val height: Float, numPointsAroundPuck: Int) {

    private val POSITION_COMPONENT_COUNT = 3
    private val vertexArray: VertexArray
    private val drawList: MutableList<DrawCommand>

    init{
        val generatedData = createPuck(
                Cylinder(Point(0f, 0f, 0f), radius, height), numPointsAroundPuck
        )

        vertexArray = VertexArray(generatedData.vertexData)
        drawList = generatedData.drawList
    }

    fun bindData(colorProgram: ColorShaderProgram){
        vertexArray.setVertexAttribPointer(0, colorProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, 0)
    }

    fun draw(){
        drawList.forEach{
            it.invoke()
        }
    }

}