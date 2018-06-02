package ru.chipenable.openglhockey.objects

import android.opengl.GLES20.GL_TRIANGLE_FAN
import android.opengl.GLES20.glDrawArrays
import ru.chipenable.openglhockey.Constants
import ru.chipenable.openglhockey.data.VertexArray
import ru.chipenable.openglhockey.programs.TextureShaderProgram

/**
 * Created by Pavel.B on 02.06.2018.
 */
class Table {

    private val POSITION_COMPONENT_COUNT = 2
    private val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
    private val STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) *
            Constants.BYTES_PER_FLOAT

    private val VERTEX_DATA = floatArrayOf(
            // Order of coordinates: X, Y, S, T

            // Triangle Fan
               0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 0.9f,
             0.5f, -0.8f,   1f, 0.9f,
             0.5f,  0.8f,   1f, 0.1f,
            -0.5f,  0.8f,   0f, 0.1f,
            -0.5f, -0.8f,   0f, 0.9f
    )

    private val vertexArray: VertexArray

    init{
        vertexArray = VertexArray(VERTEX_DATA)
    }

    fun bindData(textureProgram: TextureShaderProgram){
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE)

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.aTextureCoordinatesLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE)
    }

    fun draw(){
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)
    }
}