package ru.chipenable.openglhockey.objects

import ru.chipenable.openglhockey.data.VertexArray
import ru.chipenable.openglhockey.programs.ColorShaderProgram
import ru.chipenable.openglhockey.util.Point

/**
 * Created by Pavel.B on 02.06.2018.
 */
class Mallet(radius: Float, val height: Float, numPointsAroundMallet: Int) {

    private val POSITION_COMPONENT_COUNT = 3
    private val vertexArray: VertexArray
    private val drawList: MutableList<DrawCommand>

    init{
        val generatedData = createMallet(Point(0f, 0f, 0f), radius, height, numPointsAroundMallet)
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