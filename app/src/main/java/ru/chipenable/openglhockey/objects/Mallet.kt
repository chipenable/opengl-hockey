package ru.chipenable.openglhockey.objects

import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.glDrawArrays
import ru.chipenable.openglhockey.Constants
import ru.chipenable.openglhockey.data.VertexArray
import ru.chipenable.openglhockey.programs.ColorShaderProgram
import ru.chipenable.openglhockey.programs.TextureShaderProgram

/**
 * Created by Pavel.B on 02.06.2018.
 */
class Mallet {

    private val POSITION_COMPONENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3
    private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) *
            Constants.BYTES_PER_FLOAT

    private val VERTEX_DATA = floatArrayOf(
            // Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f,  0.4f, 1f, 0f, 0f
    )

    private val vertexArray: VertexArray

    init{
        vertexArray = VertexArray(VERTEX_DATA)
    }

    fun bindData(colorProgram: ColorShaderProgram){
        vertexArray.setVertexAttribPointer(
                0,
                colorProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE)

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.aColorLocation,
                COLOR_COMPONENT_COUNT,
                STRIDE)
    }

    fun draw(){
        glDrawArrays(GL_POINTS, 0, 2)
    }

}